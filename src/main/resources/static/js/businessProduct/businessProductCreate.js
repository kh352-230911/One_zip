document.querySelector("#createOptionBtn").addEventListener('click', (e) => {
    e.preventDefault();
    const innerOption =  document.querySelector("#innerOption")
    const innerOptionIndex = innerOption.childElementCount / 3;
    innerOption.insertAdjacentHTML('beforeend', `
    <input type="text" class="form-control mb-3" placeholder="옵션 명" name="innerOptionName${innerOptionIndex}" id="innerOptionName${innerOptionIndex}" required>
    <input type="text" class="form-control mb-3" placeholder="옵션 재고 수량" name="innerOptionStock${innerOptionIndex}" id="innerOptionStock${innerOptionIndex}" required>
    <input type="text" class="form-control mb-3" placeholder="옵션 추가 금액" name="innerOptionPrice${innerOptionIndex}" id="innerOptionPrice${innerOptionIndex}" required>
`);
    innerOption.value = parseInt(innerOptionIndex + 1);
});

document.querySelector("#deleteOptionBtn").addEventListener('click', (e) => {
    e.preventDefault();
    const innerOption = document.querySelector("#innerOption");
    const innerOptionCount = innerOption.childElementCount;
    // 삭제될 태그의 개수가 3의 배수인지 확인
    if (innerOptionCount % 3 === 0 && innerOptionCount > 0) {
        for (let i = 0; i < 3; i++) {
            innerOption.removeChild(innerOption.lastElementChild);
        }
        // 새로운 인덱스 계산 및 적용
        const innerOptionIndex = innerOption.childElementCount / 3;
        innerOption.value = parseInt(innerOptionIndex);
    } else {
        console.log("삭제할 항목이 없습니다.");
    }
});