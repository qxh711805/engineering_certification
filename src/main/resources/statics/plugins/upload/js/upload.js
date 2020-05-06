/* 
*	jQuery文件上传插件,封装UI,上传处理操作采用Baidu WebUploader;
*/

(function( $ ) {
	
    $.fn.extend({
		/*
		*	上传方法 opt为参数配置;
		*	serverCallBack回调函数 每个文件上传至服务端后,服务端返回参数,无论成功失败都会调用 参数为服务器返回信息;
		*/
        upload:function( opt, serverCallBack ) {
 			if ( typeof opt != "object" ) {
				alert('参数错误!');
				return;	
			}
			
			var $fileInput = $(this);
			var $fileInputId = $fileInput.attr('id');
			
			//组装参数;
			if( opt.url ) {
				opt.server = opt.url; 
				delete opt.url;
			}
			
			
			if( opt.success ) {
				var successCallBack = opt.success;
				delete opt.success;
			}
			
			if( opt.error ) {
				var errorCallBack = opt.error;
				delete opt.error;
			}
			
			//迭代出默认配置
			$.each( getOption( '#'+$fileInputId ),function( key, value ){
					opt[ key ] = opt[ key ] || value; 
			});
			
			if ( opt.buttonText ) {
				opt['pick']['label'] = opt.buttonText;
				delete opt.buttonText;	
			}
			
			if( typeof(opt.multi)!='undefined' ) {
				opt['pick']['multiple'] = opt.multi;
				delete opt.multi;
			}
			var webUploader = getUploader( opt );
			webUploader.deletefuc = opt.ondelete;
			if ( !WebUploader.Uploader.support() ) {
				alert( ' 上传组件不支持您的浏览器！');
				return false;
       		}
			
			webUploader.showlist = opt.showlist;
			if(opt.initFiles){
				initFiles( $fileInput,opt.initFiles,webUploader);
			}
			var isalert = true;
			//绑定文件加入队列事件;
			webUploader.on('fileQueued', function( file ) {
				createBox( $fileInput, file ,webUploader);
				var has = $fileInput.parents('.parentFileBox:first').find('li').length;
				if((parseInt(has))==this.options.fileNumLimit){
					$fileInput.find('label').css('cursor','not-allowed');
					$fileInput.find('input').attr('disabled','true');
				}
				if(this.options.fileNumLimit==1){
					$('#'+$fileInputId).hide();
				}
			});
			
			//进度条事件
			webUploader.on('uploadProgress',function( file, percentage  ){
				var $fileBox = $('#'+$fileInputId).parent().find('#fileBox_'+file.id);
				var $diyBar = $fileBox.find('.diyBar');	
				$diyBar.show();
				percentage = percentage*100;
				showDiyProgress( percentage.toFixed(2), $diyBar);
				
			});
			
			//全部上传结束后触发;
			webUploader.on('uploadFinished', function(){
				//$fileInput.next('.parentFileBox').children('.diyButton').remove();
			});
			//绑定发送至服务端返回后触发事件;
			webUploader.on('uploadAccept', function( object ,data ){
				if ( serverCallBack ) serverCallBack( data );
			});
			
			//上传成功后触发事件;
			webUploader.on('uploadSuccess',function( file, response ){
				
				var $fileBox = $('#'+$fileInputId).parent().find('#fileBox_'+file.id);
				var $diyBar = $fileBox.find('.diyBar');	
				var $diyCancel = $fileBox.find('.diyCancel');	
				var $fileid = $fileBox.find('#fileid');	
				$fileBox.find('.viewThumb').find('img').remove();
				$fileBox.find('.viewThumb').append('<a href="'+response.realpath+'" class="swipebox" title=""><img src="'+response.realpath+'" ></a>');
				$fileid.val(response.fileid);
				$diyCancel.attr('attachment-path',response.path);
				//$diyCancel.attr('attachment-lan',response.lan);
				//$fileBox.removeClass('diyUploadHover');
				$diyBar.fadeOut( 1000 ,function(){
					//$fileBox.children('.diySuccess').show();
				});
				if ( successCallBack ) {
					successCallBack( response ,webUploader,file);
				}	
			});
			
			//上传失败后触发事件;
			webUploader.on('uploadError',function( file, reason ){
				
				var $fileBox = $('#'+$fileInputId).parent().find('#fileBox_'+file.id);
				var $diyBar = $fileBox.find('.diyBar');	
				showDiyProgress( 0, $diyBar , '上传失败!' );
				var err = '上传失败! 文件:'+file.name+' 错误码:'+reason;
				if ( errorCallBack ) {
					errorCallBack( err );
				}
			});
			
			webUploader.on('beforeFileQueued',function( file, reason ){
				
				var has = $fileInput.parents('.parentFileBox:first').find('li').length;
//				if((parseInt(has)+1)==this.options.fileNumLimit){
//					$fileInput.find('label').css('cursor','not-allowed');
//					$fileInput.find('input').attr('disabled','true');
//				}
				if(parseInt(has)>=this.options.fileNumLimit){
					if(isalert==true){
						alert("上传文件数量最大为 "+this.options.fileNumLimit);
						isalert = false;
						if((parseInt(has))==this.options.fileNumLimit){
							$fileInput.find('label').css('cursor','not-allowed');
							$fileInput.find('input').attr('disabled','true');
						}
					}
					return false;
				}
				
				
				//alert(has);
				//var s = this.getStats();
				//s.successNum
			});
			webUploader.on('fileDequeued',function( file, reason ){
				/*var has = $fileInput.parents().find('.parentFileBox').find('li').length;
				has = has - 1;
				if(has < this.options.fileNumLimit){
					isalert = true;
					$fileInput.find('label').css('cursor','pointer');
					$fileInput.find('input').removeAttr('disabled');
				}
				$('.webuploader-container').show();*/
			});
			
			//选择文件错误触发事件;
			webUploader.on('error', function( code ) {
				
				var text = '';
				switch( code ) {
					case  'F_DUPLICATE' : text = '该文件已经被选择了!' ;
					break;
					case  'Q_EXCEED_NUM_LIMIT' : text = '上传文件数量超过限制!' ;
					break;
					case  'F_EXCEED_SIZE' : text = '文件大小超过限制!';
					break;
					case  'Q_EXCEED_SIZE_LIMIT' : text = '所有文件总大小超过限制!';
					break;
					case 'Q_TYPE_DENIED' : text = '文件类型不正确或者是空文件!';
					break;
					default : text = '未知错误!';
 					break;	
				}
            	alert( text );
        	});
        }
    });
	
	//Web Uploader默认配置;
	function getOption(objId) {
		/*
		*	配置文件同webUploader一致,这里只给出默认配置.
		*	具体参照:http://fex.baidu.com/webuploader/doc/index.html
		*/
		return {
			swf:ctxStatic + "/upload/js/Uploader.swf",
			//按钮容器;
			pick:{
				id:objId,
				label:"上传"
			},
			//类型限制;
			accept:{
				title:"Images",
				extensions:"gif,jpg,jpeg,bmp,png",
				mimeTypes:"image/*"
			},
			duplicate:true,
			auto :true,
			//配置生成缩略图的选项
			thumb:{
				width:100,
				height:100,
				// 图片质量，只有type为`image/jpeg`的时候才有效。
				quality:100,
				// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
				allowMagnify:false,
				// 是否允许裁剪。
				crop:false,
				// 为空的话则保留原有图片格式。
				// 否则强制转换成指定的类型。
				type:"image/jpeg"
			},
			//文件上传方式
			method:"POST",
			//服务器地址;
			server:"",
			//是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容
			sendAsBinary:false,
			// 开起分片上传。 thinkphp的上传类测试分片无效,图片丢失;
			chunked:false,
			// 分片大小
			chunkSize:512 * 1024,
			//最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
			fileNumLimit:1,
			fileSizeLimit:5000 * 1024,
			fileSingleSizeLimit:5000 * 1024
		};
	}
	
	//实例化Web Uploader
	function getUploader( opt ) {
		return new WebUploader.Uploader( opt );;
	}
	
	//操作进度条;
	function showDiyProgress( progress, $diyBar, text ) {
		if ( progress >= 100 ) {
			progress = progress + '%';
			text = text || '上传完成';
		} else {
			progress = progress + '%';
			text = text || progress;
		}
		var $diyProgress = $diyBar.find('.diyProgress');
		var $diyProgressText = $diyBar.find('.diyProgressText');
		$diyProgress.width( progress );
		$diyProgressText.text( text );
	
	}
	
	//取消事件;	
	function removeLi ( $li ,file_id ,webUploader,$fileInput) {
		var path = $li.find('.diyCancel').attr('attachment-path');
		//var id = $li.find('.diyCancel').attr('attachment-id');
		//$.getJSON(GV.DIMAUB+'index.php?g=Admin&m=Common&a=delUpload',{path:path},function(result){
		var result = {status:true};
			if(result.status == true){
				try{
					webUploader.removeFile( file_id );
				}catch(e){}
				
				var has = $fileInput.parents('.parentFileBox:first').find('li').length;
				has = has - 1;
				if(has < webUploader.options.fileNumLimit){
					isalert = true;
					$fileInput.find('label').css('cursor','pointer');
					$fileInput.find('input').removeAttr('disabled');
				}
				$('#'+$fileInput.attr('id')).show();
				//if ( $li.siblings('li').length <= 0 ) {
					//$li.parents('.parentFileBox').remove();
				//} else {
					$li.remove();
				//}
				
				var fileid=$li.find("#fileid").val();
				if(webUploader.deletefuc){
					webUploader.deletefuc(fileid,path);
				}
			}else{
				
			}
		//});
	}
	
	//创建文件操作div;	
	function createBox( $fileInput, file, webUploader ) {
		var file_id = file.id;
		var $parentFileBox = $fileInput.parents('.parentFileBox:first');
		//添加子容器;
		var li = '<li id="fileBox_'+file_id+'" class="diyUploadHover"> \
					<div class="viewThumb"><span style="height:100%;display:inline-block;vertical-align:middle"></span></div> \
					<input id="fileid" name="fileid" type="hidden"> \
					<div class="diyCancel"></div> \
					<div class="diySuccess"></div> \
					<div class="diyFileName">点击查看大图</div>\
					<div class="diyBar"> \
							<div class="diyProgress"></div> \
							<div class="diyProgressText">0%</div> \
					</div> \
				</li>';
		$parentFileBox.children('.fileBoxUl').children('#'+$fileInput.attr('id')).before( li );
		
		var $fileBox = $parentFileBox.find('#fileBox_'+file_id);
		if(typeof(webUploader.showlist)!='undefined' && webUploader.showlist == false){
			$fileBox.hide();
		}else{
			$fileBox.show();
		}

		//绑定取消事件;
		var $diyCancel = $fileBox.children('.diyCancel').one('click',function(){
			removeLi( $(this).parent('li'), file_id, webUploader,$fileInput );	
		});
		
		if ( file.type.split("/")[0] != 'image' ) {
			var liClassName = getFileTypeClassName( file.name.split(".").pop() );
			$fileBox.addClass(liClassName);
			return;	
		}
		
		//生成预览缩略图;
		webUploader.makeThumb( file, function( error, dataSrc ) {
			if ( !error ) {	
				$fileBox.find('.viewThumb').append('<img src="'+dataSrc+'" >');
			}
		});	
	}
	
	//初始化文件;	
	function initFiles( $fileInput,initFiles, webUploader) {
		var uploadroot='/data/upload/';
		var files = initFiles.split(',');
		var index = 0;
		for(var i in files){
			index--;
			if(files[i]==''){continue}
			var $parentFileBox = $fileInput.parents('.parentFileBox:first');
			//添加子容器;
			var li = '<li id="fileBox_'+index+'" class="diyUploadHover"> \
						<div class="viewThumb"><span style="height:100%;display:inline-block;vertical-align:middle"></span></div> \
						<div class="diyCancel" attachment-path="'+files[i].replace(uploadroot,'')+'"></div> \
						<div class="diySuccess"></div> \
						<div class="diyFileName"></div>\
						<div class="diyBar"> \
								<div class="diyProgress"></div> \
								<div class="diyProgressText">0%</div> \
						</div> \
					</li>';
			$parentFileBox.children('.fileBoxUl').children('#'+$fileInput.attr('id')).before( li );
			
			var $fileBox = $parentFileBox.find('#fileBox_'+index);
			if(typeof(webUploader.showlist)!='undefined' && webUploader.showlist == false){
				$fileBox.hide();
			}else{
				$fileBox.show();
			}
			//绑定取消事件;
			var $diyCancel = $fileBox.children('.diyCancel').one('click',function(){
				removeLi( $(this).parent('li'), index, webUploader ,$fileInput);	
			});
			
			//if ( file.type.split("/")[0] != 'image' ) {
				//var liClassName = getFileTypeClassName( initFiles.split(".").pop() );
				//$fileBox.addClass(liClassName);
				//return;	
			//}
				setTimeout(function(){
					if(webUploader.options.fileNumLimit==1){
						$fileInput.find('label').css('cursor','pointer');
						$fileInput.find('input').removeAttr('disabled');
						$('#'+$fileInput.attr("id")).hide();
					}
					var has = $fileInput.parents('.parentFileBox:first').find('li').length;
					if((parseInt(has))==webUploader.options.fileNumLimit){
						$fileInput.find('label').css('cursor','not-allowed');
						$fileInput.find('input').attr('disabled','true');
					}
				},100);
			if(typeof(webUploader.showlist)!='undefined' && webUploader.showlist == false){
				continue;
			}
			
			var ext = files[i].split(".").pop();
			
			if('gif,jpg,jpeg,bmp,png'.indexOf(ext)==-1){
				var liClassName = getFileTypeClassName( files[i].split(".").pop() );
				$fileBox.addClass(liClassName);
				continue;
			}
			
			//生成预览缩略图;
			//webUploader.makeThumb( file, function( error, dataSrc ) {
				//if ( !error ) {	
					$fileBox.find('.viewThumb').append('<a href="'+files[i]+'" class="swipebox" title=""><img src="'+files[i]+'" ></a>');
				//}
			//});	
			
		}
		
	}
	
	//获取文件类型;
	function getFileTypeClassName ( type ) {
		var fileType = {};
		var suffix = '_diy_bg';
		fileType['pdf'] = 'pdf';
		fileType['zip'] = 'zip';
		fileType['rar'] = 'rar';
		fileType['csv'] = 'csv';
		fileType['doc'] = 'doc';
		fileType['xls'] = 'xls';
		fileType['xlsx'] = 'xls';
		fileType['txt'] = 'txt';
		fileType = fileType[type] || 'txt';
		return 	fileType+suffix;
	}
	
})( jQuery );

$(function(){
	$(".swipebox").swipebox();
})
