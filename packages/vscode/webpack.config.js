const path = require('path');

module.exports = {
  mode: 'development',
  entry: './src/panel/index.js',
  output: {
    filename: 'panel.js',
    path: path.resolve(__dirname, 'out', 'panel'),
    libraryTarget: "commonjs2",
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react'],
          },
        },
      },
    ],
  },
  target: 'node',
  resolve: {
    extensions: ['.js', '.jsx'],
  },
  devtool: 'source-map',
  externals: {
    vscode: 'commonjs vscode',
  },
};