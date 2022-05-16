module.exports = {
	devServer: {
		proxy: {
			// 配置跨域
			'/upPic':{
				target: 'https://sm.ms', 
				ws: true,
				changOrigin: true,
				pathRewrite: {
					'^/upPic':''
				}
			},
			'/api':{
				target: 'http://localhost:55555', //后端接口地址
				ws: true,
				changOrigin: true,
				pathRewrite: {
					'^/api':''
				}
			},
			
		}
	}
}
