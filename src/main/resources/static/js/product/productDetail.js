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
// document.querySelector("#selectOption").addEventListener('change', (e) => {
//     // 선택된 옵션 요소를 가져옵니다.
//     const selectedOption = e.target.options[e.target.selectedIndex];
//     const selectedOptionIndex = selectedOption.value;
//     // console.log(selectedOptionIndex);
//     //
//     // const temp = '#Seloption' + selectedOptionIndex.toString();
//     // console.log(temp)
//     const selOptVal = e.target.value;
//     const stringArr = selOptVal.split('#');
//     const optionCost = stringArr[1];
//
//     const optionHiddenCostEle = document.querySelector('#refOptionCost');
//     optionHiddenCostEle.value = parseInt(optionCost);
//
//
//     const product_quantityEle = document.querySelector("#product-quantity");
//     // const product_quantity = parseInt(product_quantityEle.innerText); // innerHTML 대신 innerText 사용
//     const product_quantity = parseInt(product_quantityEle.value);
//
//     const productSellPriceEle = document.querySelector('#product-sellPrice');
//     const productSellPrice = parseInt(productSellPriceEle.innerText);
//     // const productSellPrice = parseInt(productSellPriceEle.value);
//
//
//     const refOptionCostEle = document.querySelector("#refOptionCost");
//     const refOptionCostPrice = parseInt(refOptionCostEle.value);
//
//     const productTotalPriceEle = document.querySelector('#product-totalPrice');
//     productTotalPriceEle.value = (productSellPrice + refOptionCostPrice) * product_quantity;
//     // productTotalPriceEle.value = (productSellPrice + optionCost) * product_quantity;
// });


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

document.querySelectorAll("tr[data-product-id]").forEach((tr) => {
    tr.addEventListener('click', (e) => {
        const td = e.target;
        const tr = td.parentElement;
        const {productId} = tr.dataset;
        location.href = `${contextPath}product/productQna.do?id=${productId}`;
    });
});

function submitForm(action) {
    console.log(action + ' 액션 실행중');

    var form = document.getElementById('myForm');

    // 액션에 따라 폼의 액션을 설정합니다.
    if (action === 'purchase') {
        form.action = contextPath + 'product/productPurchaseInfo.do';
    } else if (action === 'cart') {
        form.action = contextPath + 'product/productCart.do';
    }

    // 폼을 서버로 전송합니다.
    form.submit();
}

// function productPurchase() {
//         console.log('productPurchase() 실행중');
//     // document.getElementById('myForm').action = `${contextPath}product/productPurchaseInfo.do`;
//     location.href = `${contextPath}product/productPurchaseInfo.do`;
//     document.getElementById('myForm').submit();
// }
//
// function productCart() {
//     console.log('productCart() 실행중');
//     document.getElementById('myForm').action = `${contextPath}product/productCart.do`;
//     document.getElementById('myForm').submit();
// }

// function submitForm(endpointURL) {
//     const csrfToken = document.getElementById('csrfToken').value;
//     const form = document.getElementById('myForm');
//
//     // 선택된 URL로 폼의 action을 설정
//     form.action = endpointURL;
//
//     // 새로운 CSRF 토큰을 생성하여 폼에 추가
//     const csrfInputElement = document.createElement('input');
//     const csrfTokenEle = document.querySelector("#csrfToken");
//
//     csrfInputElement.type = 'hidden';
//     csrfInputElement.name = csrfTokenEle.name;
//     csrfInputElement.value = csrfToken;
//     form.appendChild(csrfInputElement);
//
//     // 폼 제출
//     form.submit();
// }