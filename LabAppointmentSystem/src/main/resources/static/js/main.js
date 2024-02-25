const imgContainer = document.querySelector('.img-container')
const btnLeft = document.querySelector('.btn-left')
const btnRight = document.querySelector('.btn-right')
const images = document.querySelectorAll('.img-container img')

let currentSlide = 0
let interval = setInterval(autorun,4000)

function autorun(){
	currentSlide++
	changeImage()
}

function changeImage(){
	if(currentSlide > images.length -1){
		currentSlide = 0
	}else if(currentSlide < 0){
		currentSlide = images.length -1
	}
	
	imgContainer.style.transform = `translateX(-${
		currentSlide * imgContainer.offsetWidth
	}px)`
}

function resetInterval(){
	clearInterval(interval)
	interval = setInterval(autorun,4000)
}

btnRight.addEventListener('click',function(){
	currentSlide++
	changeImage()
	resetInterval()
})

btnLeft.addEventListener('click',function(){
	currentSlide--
	changeImage()
	resetInterval()
})

