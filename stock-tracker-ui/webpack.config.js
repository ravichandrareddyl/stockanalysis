var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var lessToJs = require('less-vars-to-js');
var fs = require('fs');
var themeVariables = lessToJs(fs.readFileSync(path.join(__dirname, './less/ant-default-vars.less'), 'utf8'));

module.exports = {
    entry: './src/index.jsx',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js'
    },
    resolve: {
        extensions: ['.js', '.jsx']
    },
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2015', 'stage-3'],
                    plugins: [
                        ['import', { libraryName: 'antd', style: true}]
                    ]
                }
            },
            {
                test: /\.less$/,
                use: [
                  {loader: 'style-loader'},
                  {loader: 'css-loader'},
                  {loader: 'less-loader',
                    options: {
                      modifyVars: themeVariables,
                      javascriptEnabled: true
                    }
                  }
                ]
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    plugins: [new HtmlWebpackPlugin({
        template: './src/index.html',
        filename: 'index.html',
        inject: 'body'
    })],
    devServer: {
        historyApiFallback: true
    },
    externals: {
        // global app config object
        config: JSON.stringify({
            apiUrl: 'http://localhost:8083/api'
        })
    }
}