package com.stocks.analysis.utils;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.jdbc.ExpressionEvaluatingSqlParameterSourceFactory;
import org.springframework.integration.jdbc.SqlParameterSourceFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.messaging.Message;

public class JdbcIntPoller  extends IntegrationObjectSupport implements MessageSource<Object> {

	private final NamedParameterJdbcOperations jdbcOperations;

	private final String selectQuery;

	private volatile RowMapper<?> rowMapper;

	private volatile SqlParameterSource sqlQueryParameterSource;

	private volatile boolean updatePerRow = false;

	private volatile String updateSql;

	private volatile SqlParameterSourceFactory sqlParameterSourceFactory =
			new ExpressionEvaluatingSqlParameterSourceFactory();

	private volatile boolean sqlParameterSourceFactorySet;

	private volatile int maxRowsPerPoll = 0;

	/**
	 * Constructor taking {@link DataSource} from which the DB Connection can be
	 * obtained and the select query to execute to retrieve new rows.
	 *
	 * @param dataSource Must not be null
	 * @param selectQuery query to execute
	 */
	public JdbcIntPoller(DataSource dataSource, String selectQuery) {
		this.jdbcOperations = new NamedParameterJdbcTemplate(dataSource);
		this.selectQuery = selectQuery;
	}

	/**
	 * Constructor taking {@link JdbcOperations} instance to use for query
	 * execution and the select query to execute to retrieve new rows.
	 *
	 * @param jdbcOperations instance to use for query execution
	 * @param selectQuery query to execute
	 */
	public JdbcIntPoller(JdbcOperations jdbcOperations, String selectQuery) {
		this.jdbcOperations = new NamedParameterJdbcTemplate(jdbcOperations);
		this.selectQuery = selectQuery;
	}

	public void setRowMapper(RowMapper<?> rowMapper) {
		this.rowMapper = rowMapper;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public void setUpdatePerRow(boolean updatePerRow) {
		this.updatePerRow = updatePerRow;
	}

	public void setUpdateSqlParameterSourceFactory(SqlParameterSourceFactory sqlParameterSourceFactory) {
		this.sqlParameterSourceFactory = sqlParameterSourceFactory;
		this.sqlParameterSourceFactorySet = true;
	}

	/**
	 * A source of parameters for the select query used for polling.
	 *
	 * @param sqlQueryParameterSource the sql query parameter source to set
	 */
	public void setSelectSqlParameterSource(SqlParameterSource sqlQueryParameterSource) {
		this.sqlQueryParameterSource = sqlQueryParameterSource;
	}

	/**
	 * The maximum number of rows to pull out of the query results per poll (if
	 * greater than zero, otherwise all rows will be packed into the outgoing
	 * message). Default is zero.
	 *
	 * @param maxRows the max rows to set
	 */
	public void setMaxRowsPerPoll(int maxRows) {
		this.maxRowsPerPoll = maxRows;
	}

	@Override
	protected void onInit() throws Exception {
		super.onInit();
		if (!this.sqlParameterSourceFactorySet && this.getBeanFactory() != null) {
			((ExpressionEvaluatingSqlParameterSourceFactory) this.sqlParameterSourceFactory)
					.setBeanFactory(this.getBeanFactory());
		}
	}

	/**
	 * Execute the query. If a query result set contains one or more rows, the
	 * Message payload will contain either a List of Maps for each row or, if a
	 * RowMapper has been provided, the values mapped from those rows. If the
	 * query returns no rows, this method will return <code>null</code>.
	 * #return the {@link Message} or {@code null} as a result of query.
	 */
	@Override
	public Message<Object> receive() {
		Object payload = poll();
		if (payload == null) {
			return null;
		}
		return this.getMessageBuilderFactory().withPayload(payload).build();
	}

	/**
	 * Execute the select query and the update query if provided. Returns the
	 * rows returned by the select query. If a RowMapper has been provided, the
	 * mapped results are returned.
	 */
	private Object poll() {
		List<?> payload = doPoll(this.sqlQueryParameterSource);
		if (payload.size() < 1) {
			payload = null;
		}
		if (payload != null && this.updateSql != null) {
			if (this.updatePerRow) {
				for (Object row : payload) {
					executeUpdateQuery(row);
				}
			}
			else {
				executeUpdateQuery(payload);
			}
		}
		return payload;
	}

	private void executeUpdateQuery(Object obj) {
		SqlParameterSource updateParameterSource = this.sqlParameterSourceFactory.createParameterSource(obj);
		this.jdbcOperations.update(this.updateSql, updateParameterSource);
	}

	protected List<?> doPoll(SqlParameterSource sqlQueryParameterSource) {
		final RowMapper<?> rowMapper = this.rowMapper == null ? new ColumnMapRowMapper() : this.rowMapper;
		ResultSetExtractor<List<Object>> resultSetExtractor;

		if (this.maxRowsPerPoll > 0) {
			resultSetExtractor = rs -> {
				List<Object> results = new ArrayList<Object>(JdbcIntPoller.this.maxRowsPerPoll);
				int rowNum = 0;
				while (rs.next() && rowNum < JdbcIntPoller.this.maxRowsPerPoll) {
					results.add(rowMapper.mapRow(rs, rowNum++));
				}
				return results;
			};
		}
		else {
			@SuppressWarnings("unchecked")
			ResultSetExtractor<List<Object>> temp =
					new RowMapperResultSetExtractor<Object>((RowMapper<Object>) rowMapper);
			resultSetExtractor = temp;
		}

		if (sqlQueryParameterSource != null) {
			return this.jdbcOperations.query(this.selectQuery, sqlQueryParameterSource, resultSetExtractor);
		}
		else {
			return this.jdbcOperations.getJdbcOperations().query(this.selectQuery, resultSetExtractor);
		}
	}

	@Override
	public String getComponentType() {
		return "jdbc:inbound-channel-adapter";
	}

}