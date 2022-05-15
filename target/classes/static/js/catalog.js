let products = document.querySelectorAll(".catalog__one__product")
if(products.length > 0){
    for (let i = 0; i < products.length; i++) {
        let product = products[i]
        product.addEventListener("click", (e) => {
            let popup = document.querySelector('.catalog__chouse__popup')
            popup.classList.add('active')
            document.querySelector('main').classList.add('blur')
            let cardButton = document.querySelector('.card')
            cardButton.addEventListener("click", () => {
                location.href = `/buy/${product.id}`
            })
        })
    }
}

let closeArea = document.querySelector('.catalog__popup__close__area')
if(closeArea != null) {
    closeArea.addEventListener('click', (e) => {
        document.querySelector('.catalog__chouse__popup').classList.remove('active')
        document.querySelector('main').classList.remove('blur')
        e.preventDefault()
    })
}