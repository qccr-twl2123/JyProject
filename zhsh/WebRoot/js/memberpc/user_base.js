/**
 * Created by Administrator on 2017/5/15.
 */



//通过base64编码长度计算当前图片大小
 
function dask_hide(){
    $(".dask").hide()
}
function imgChange(ele){
    var img_src=$(ele).attr("src");
    $(".left_box img").attr("src",img_src);
    $(".dask").show()
}

function img_test(str){
    str=str.substring(22);
    var equalIndex= str.indexOf('=');
    if(str.indexOf('=')>0)
    {
        str=str.substring(0, equalIndex);

    }
    var strLength=str.length;
    var fileLength=parseInt(strLength-(strLength/8)*2);
    return parseInt(fileLength/1024)
}

 


//图片剪切
$(function () {
    var console = window.console || { log: function () {} };
    var $image = $('.left_box img');
    var options = {
        preview: '.img-preview',
        aspectRatio: 1 / 1,
        viewMode : 1,
        cropBoxMovable :false,
        cropBoxResizable :false,
        dragCrop:false
    };
    // Cropper
        $(".img_change img").click(function(){
            $image.cropper(options);
        })
    // Methods
    $('.docs-buttons').on('click', '[data-method]', function () {
        var $this = $(this);
        var data = $this.data();
        var $target;
        var result;
        if ($this.prop('disabled') || $this.hasClass('disabled')) {
            return;
        }
        if ($image.data('cropper') && data.method) {
            data = $.extend({}, data); // Clone a new one
            if (typeof data.target !== 'undefined') {
                $target = $(data.target);

                if (typeof data.option === 'undefined') {
                    try {
                        data.option = JSON.parse($target.val());
                    } catch (e) {
                        console.log(e.message);
                    }
                }
            }
            if (data.method === 'rotate') {
                $image.cropper('clear');
            }
            result = $image.cropper(data.method, data.option, data.secondOption);
            if (data.method === 'rotate') {
                $image.cropper('crop');
            }
            switch (data.method) {
                case 'scaleX':
                case 'scaleY':
                    $(this).data('option', -data.option);
                    break;
                case 'getCroppedCanvas':
                    if (result) {
                         var base64url = result.toDataURL('image/jpeg');
                         $("#base64url").val(base64url);
                         editImageUrl(base64url);
                      }
                    break;
            }
            if ($.isPlainObject(result) && $target) {
                try {
                    $target.val(JSON.stringify(result));
                } catch (e) {
                    console.log(e.message);
                }
            }
        }
    });
    // Keyboard
    $(document.body).on('keydown', function (e) {
        if (!$image.data('cropper') || this.scrollTop > 300) {
            return;
        }
        switch (e.which) {
            case 37:
                e.preventDefault();
                $image.cropper('move', -1, 0);
                break;

            case 38:
                e.preventDefault();
                $image.cropper('move', 0, -1);
                break;

            case 39:
                e.preventDefault();
                $image.cropper('move', 1, 0);
                break;

            case 40:
                e.preventDefault();
                $image.cropper('move', 0, 1);
                break;
        }

    });
    // Import image
    var $inputImage = $('#inputImage');
    var URL = window.URL || window.webkitURL;
    var blobURL;

    if (URL) {
        $inputImage.change(function () {
            var files = this.files;
            var file;

            if (!$image.data('cropper')) {
                return;
            }

            if (files && files.length) {
                file = files[0];

                if (/^image\/\w+$/.test(file.type)) {
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {

                        // Revoke when load complete
                        URL.revokeObjectURL(blobURL);
                    }).cropper('reset').cropper('replace', blobURL);
                    $inputImage.val('');
                } else {
                    window.alert('文件类型错误');
                }
            }
        });
    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }


    $($(".buttonbox div")[0]).click(function(){
        "use strict";
        $($(".buttonbox div")[0]).children("input")[0].click()
    });
   
});