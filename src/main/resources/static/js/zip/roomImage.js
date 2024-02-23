const imageUrls = [];
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

// 이미지 URL을 가져오는 요청을 보내는 함수
function fetchImageUrls() {
    fetch('/fileDownload.do?id=' + zipId)
        .then(response => response.json())
        .then(data => {
            imageUrls.push(data.imageUrl);
            updateCarousel();
        })
        .catch(error => console.error('Error fetching image URLs:', error));
}

// 페이지 로드 시 이미지 URL을 가져옵니다.
fetchImageUrls();