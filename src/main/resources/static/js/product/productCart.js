document.querySelector("#plus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantity = document.querySelector("#product-quantity")
    product_quantity.value = parseInt(product_quantity.value) + 1;
});

document.querySelector("#minus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantity = document.querySelector("#product-quantity")
    if(product_quantity.value != 1){
        product_quantity.value = parseInt(product_quantity.value) - 1;
    }
});

// select 요소에 change 이벤트 핸들러를 추가합니다.
// selectElement.addEventListener('change', function() {
if(document.querySelector("#selectOption") != null){
    document.querySelector("#selectOption").addEventListener('change', (e) => {
        // 선택된 옵션 요소를 가져옵니다.
        const selectedOption = e.target.options[e.target.selectedIndex];
        const selectedOptionIndex = selectedOption.value;
        // console.log(selectedOptionIndex);
        //
        // const temp = '#Seloption' + selectedOptionIndex.toString();
        // console.log(temp)
        const selOptVal = e.target.value;
        const stringArr = selOptVal.split('#');
        const optionCost = stringArr[1];

        const optionHiddenCostEle = document.querySelector('#refOptionCost');
        optionHiddenCostEle.value = parseInt(optionCost);


        const product_quantityEle = document.querySelector("#product-quantity");
        // const product_quantity = parseInt(product_quantityEle.innerText); // innerHTML 대신 innerText 사용
        const product_quantity = parseInt(product_quantityEle.value);

        const productSellPriceEle = document.querySelector('#product-sellPrice');
        const productSellPrice = parseInt(productSellPriceEle.innerText);
        // const productSellPrice = parseInt(productSellPriceEle.value);


        const refOptionCostEle = document.querySelector("#refOptionCost");
        const refOptionCostPrice = parseInt(refOptionCostEle.value);

        const productTotalPriceEle = document.querySelector('#product-totalPrice');
        productTotalPriceEle.value = (productSellPrice + refOptionCostPrice) * product_quantity;
        // productTotalPriceEle.value = (productSellPrice + optionCost) * product_quantity;
    });
}

document.querySelector("#plus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantityEle = document.querySelector("#product-quantity");
    // const product_quantity = parseInt(product_quantityEle.innerText); // innerHTML 대신 innerText 사용
    const product_quantity = parseInt(product_quantityEle.value);

    const productSellPriceEle = document.querySelector('#product-sellPrice');
    const productSellPrice = parseInt(productSellPriceEle.innerText);
    // const productSellPrice = parseInt(productSellPriceEle.value);


    const refOptionCostEle = document.querySelector("#refOptionCost");
    const refOptionCostPrice = parseInt(refOptionCostEle.value);


    const productTotalPriceEle = document.querySelector('#product-totalPrice');
    productTotalPriceEle.value = (productSellPrice + refOptionCostPrice) * product_quantity;
});

document.querySelector("#minus-btn").addEventListener('click', (e) => {
    e.preventDefault();
    const product_quantityEle = document.querySelector("#product-quantity");
    const product_quantity = parseInt(product_quantityEle.value);

    const productSellPriceEle = document.querySelector('#product-sellPrice');
    const productSellPrice = parseInt(productSellPriceEle.innerText);

    const refOptionCostEle = document.querySelector("#refOptionCost");
    const refOptionCostPrice = parseInt(refOptionCostEle.value);


    const productTotalPriceEle = document.querySelector('#product-totalPrice');
    productTotalPriceEle.value = (productSellPrice + refOptionCostPrice) * product_quantity;
});

document.querySelector("#product-quantity").addEventListener('input', (e) => {
    e.preventDefault();
    const product_quantityEle = document.querySelector("#product-quantity");
    if(parseInt(product_quantityEle.value) < 1){
        product_quantityEle.value = 1;
    }
    const product_quantity = parseInt(product_quantityEle.value);

    const productSellPriceEle = document.querySelector('#product-sellPrice');
    const productSellPrice = parseInt(productSellPriceEle.innerText);

    const productTotalPriceEle = document.querySelector('#product-totalPrice');
    if(isNaN(parseInt(product_quantityEle.value))){
        productTotalPriceEle.value = productSellPrice;
    }
    else{
        productTotalPriceEle.value = productSellPrice * product_quantity;
    }
});