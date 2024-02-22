const imageUrls = [
    'images/방.jpg', // 상대 경로 사용
    'images/방2.jpg',
    'images/방3.jpg'
];
let currentIndex = 0;

const roomImageCarousel = document.getElementById('roomImageCarousel');
const carouselImage = document.getElementById('carouselImage');
const prevButton = document.getElementById('prevButton');
const nextButton = document.getElementById('nextButton');

function updateCarousel() {
    carouselImage.src = imageUrls[currentIndex];
    prevButton.disabled = currentIndex === 0;
    nextButton.disabled = currentIndex === imageUrls.length - 1;
}

prevButton.addEventListener('click', () => {
    if (currentIndex > 0) {
        currentIndex--;
        updateCarousel();
    }
});

nextButton.addEventListener('click', () => {
    if (currentIndex < imageUrls.length - 1) {
        currentIndex++;
        updateCarousel();
    }
});

// Initialize carousel
updateCarousel();