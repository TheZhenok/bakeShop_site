let registerButton = document.querySelector('.header__nav__signUp')
registerButton.addEventListener('click', (e) => {
    document.querySelector('.register').classList.add('active')
    e.preventDefault()
})

let registerCloseArea = document.querySelector('.register__close__area')
registerCloseArea.addEventListener('click', (e) => {
    document.querySelector('.register').classList.remove('active')
    e.preventDefault()
})


let loginButton = document.querySelector('.header__nav__signIn')
loginButton.addEventListener('click', (e) => {
    document.querySelector('#log').classList.add('active')
    e.preventDefault()
})

let loginCloseArea = document.querySelector('#close__login')
loginCloseArea.addEventListener('click', (e) => {
    document.querySelector('#log').classList.remove('active')
    e.preventDefault()
})

let inputs = document.querySelectorAll('input')
if(inputs.length != 0){
    for (let index = 0; index < inputs.length; index++) {
        const input = inputs[index];
        input.addEventListener('keydown', () => {
            input.classList.add('click')
            setTimeout(() => {
                input.classList.remove('click')
            }, 100);
        })
        
    }
}


let isFocus = false
let timeToCloseSearch;

let searchBtn = document.querySelector('.header__nav__search__btn')
searchBtn.addEventListener('mouseover', () => {
    let searchInput = document.querySelector('.header__nav__search__input')
    searchInput.classList.add('active')
    searchInput.addEventListener('mouseover', () => {
        clearTimeout(timeToCloseSearch)
        searchInput.classList.add('active')
        searchInput.addEventListener('mouseout', () => {
            if(!isFocus){
                timeToCloseSearch = setTimeout(() => {
                    let searchInput = document.querySelector('.header__nav__search__input')
                    searchInput.classList.remove('active')
                }, 300);
            }
        })
    })

    searchInput.addEventListener('focus', () => {
        isFocus = true
    })

    searchInput.addEventListener('blur', () => {
        isFocus = false
        if(!isFocus){
            timeToCloseSearch = setTimeout(() => {
                let searchInput = document.querySelector('.header__nav__search__input')
                searchInput.classList.remove('active')
            }, 300);
        }
    })
})

searchBtn.addEventListener('mouseout', () => {
    if(!isFocus){
        timeToCloseSearch = setTimeout(() => {
            let searchInput = document.querySelector('.header__nav__search__input')
            searchInput.classList.remove('active')
        }, 300);
    }
})

