let links = document.querySelectorAll('.link__admin')
let block = document.querySelector('.color__btn')
let isLeft = true

if (links.length > 0) {
    links.forEach(element => {
        element.addEventListener('click', (e) => {
            if (element.innerHTML == 'Котлы' && isLeft) {
                isLeft = false
                block.classList.add('go__left')
                document.querySelector('#product__admin').style.display = ""
                document.querySelector('#user__admin').style.display = "none"
                setTimeout(() => {
                    block.style.transform = 'translate(100%, 0)'
                    block.classList.remove('go__left')
                }, 300);
            } else if (element.innerHTML == 'Пользователи' && !isLeft) {
                isLeft = true
                block.classList.add('go__right')
                document.querySelector('#product__admin').style.display = "none"
                document.querySelector('#user__admin').style.display = ""
                setTimeout(() => {
                    block.style.transform = 'translate(0, 0)'
                    block.classList.remove('go__right')
                }, 300);
            }


        })
    })
}
