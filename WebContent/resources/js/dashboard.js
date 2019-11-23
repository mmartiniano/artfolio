$("#imgFile").change(() => { previewImage() });

function previewImage(){ 

	var files = document.getElementById("imgFile").files;

	var reader = new FileReader();

	reader.readAsDataURL(files[0]);
	reader.onload = function (event) {
		$("#image-placeholder").hide();
		$("#image-result").remove();
		$("#input-file-div").append("<img id='image-result' src="+event.target.result+" class='responsive-img'>")
	};	
	
};