let path = require ('path');
let ExtractTextPlugin = require ('extract-text-webpack-plugin');

let clientSrcPath = path.resolve (__dirname, 'src');
let serverSrcPath = path.resolve (__dirname, 'build/');

module.exports = {
    entry: clientSrcPath + '/index.jsx',
    output: {
        path: serverSrcPath,
        filename: 'js/app.bundle.js'
    },
    plugins: [
        new ExtractTextPlugin('css/styles.css'),
    ],
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                loader: 'babel-loader'
            },
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({ fallback: 'style-loader', use: 'css-loader' })
            },
            {
                test: /\.(png|jpg|svg|gif)$/,
                use: {
                    loader: 'url-loader'
                }
            },
            {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'url-loader?limit=10000&mimetype=application/font-woff'
            },

            {
                test: /\.(ttf|eot)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'file-loader'
            },
            {
                test: /\.otf(\?.*)?$/,
                use: 'file-loader?name=/fonts/[name].  [ext]&mimetype=application/font-otf'
            }
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx']
    }
};