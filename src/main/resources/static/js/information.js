//Side project to make UI better

$(document).ready(function () {

    $('.add-more').click(function () {
       $(this).parent().next().removeClass('d-none');
    });

    $('.remove-me').click(function () {
       $(this).parent().addClass('d-none');
    });

    $('.btnNext').click(function(){
        $('.nav-tabs > .active').next('a').trigger('click');
    });

    $('.btnPrevious').click(function(){
        $('.nav-tabs > .active').prev('a').trigger('click');
    });

    const apikey = 'Ahwzz5SuxQ1mnZ7T4G5iuz';
    const client = filestack.init(apikey);
    const fileInput = document.getElementById('insuranceUrl');
    const options = {
        displayMode: 'inline',
        container: '#inline',
        maxFiles: 20,
        uploadInBackground: false,
        onUploadDone: updateForm,
    };
    const picker = client.picker(options);
    picker.open();

    function updateForm (result) {
        const fileData = result.filesUploaded[0];
        fileInput.value = fileData.url;

        // If file is resizable image, resize and embed it as a thumbnail preview
        if (['jpeg', 'png'].indexOf(fileData.mimetype.split('/')[1]) !== -1) {
            const container = document.getElementById('thumbnail-container');
            const thumbnail = document.getElementById('thumbnail') || new Image();
            thumbnail.id = 'thumbnail';
            thumbnail.src = client.transform(fileData.handle, {
                resize: {
                    width: 200
                }
            });

            if (!container.contains(thumbnail)) {
                container.appendChild(thumbnail);
            }
        }
        document.getElementById('insuranceUrl').value = result.filesUploaded[0].url
        picker.close();
    }

});