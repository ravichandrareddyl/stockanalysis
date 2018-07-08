package com.stocks.analysis.jobs;

import javax.sql.DataSource;

import com.stocks.analysis.constants.AppConstants;
import com.stocks.analysis.model.BavModel;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@EnableBatchProcessing
@Configuration
public class BavJobConfig {

  @Autowired
  private DataSource dataSource;
  
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
  
  @Autowired
  private Step renameFile;
	
  @Autowired
  private JobExecutionListener listener;
  
  @Bean
  public JdbcBatchItemWriter<BavModel> bavWriter() {
      JdbcBatchItemWriter<BavModel> writer = new JdbcBatchItemWriter<BavModel>();
      writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<BavModel>());
      writer.setSql(AppConstants.BAV_WRITER_SQL);
      writer.setDataSource(dataSource);
      return writer;
  }
  
  
  @Bean
  @StepScope
  public FlatFileItemReader<BavModel> bavReader(@Value("#{jobParameters['input.file.name']}") String resource) {
	FlatFileItemReader<BavModel> reader = new FlatFileItemReader<BavModel>();
    reader.setResource(new FileSystemResource(resource));
    reader.setLinesToSkip(1);
    // reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy() {{
    //     set
    // }});
    //reader.setLine
    reader.setLineMapper(new DefaultLineMapper<BavModel>() {{
        setLineTokenizer(new DelimitedLineTokenizer() {{
            setNames(AppConstants.BAV_COLUMNS);
            setDelimiter(",");
        }});
        setFieldSetMapper(new BeanWrapperFieldSetMapper<BavModel>() {{
            setTargetType(BavModel.class);
        }});
    }});
    return reader;
  }
 
 
 
  @Bean
  Step bavStep() {
    return stepBuilderFactory.get("bavStep")
    		.<BavModel, BavModel> chunk(AppConstants.CHUNK_SIZE)
    		.reader(bavReader(null))
    		.writer(bavWriter())
    		//.processor(fcProcessor)
    		.listener(listener)
    		.build();
  }

  @Bean(name = "bavJob")
  public Job bavJob() {
    return jobBuilderFactory.get("bavJob")
    		.incrementer(new RunIdIncrementer())
    		.start(bavStep())
    		.next(renameFile)
    		.listener(listener)
    		.build();
  }
}