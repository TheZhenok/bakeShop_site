document.querySelector('#product__add__admin').style.display = "none"
document.querySelector('#user__add__admin').style.display = "none"
document.querySelector('#user__admin').style.display = "none"
let links = document.querySelectorAll('.link__admin')
let block = document.querySelector('.color__btn')
let isLeft = 0

if (links.length > 0) {
    links.forEach(element => {
        element.addEventListener('click', (e) => {
            if (element.innerHTML == 'Котлы' && isLeft != 0) {
                isLeft = 0
                document.querySelector('#product__admin').style.display = ""
                document.querySelector('#product__add__admin').style.display = "none"
                document.querySelector('#user__add__admin').style.display = "none"
                document.querySelector('#user__admin').style.display = "none"
                block.style.transform += 'scaleX(2)'
                setTimeout(() => {
                    block.style.transform = 'translate(0, 0) scaleX(1)'
                    block.classList.remove('check')
                }, 150);
            }
            else if (element.innerHTML == 'Пользователи' && isLeft != 1) {
                isLeft = 1
                document.querySelector('#product__admin').style.display = "none"
                document.querySelector('#product__add__admin').style.display = "none"
                document.querySelector('#user__add__admin').style.display = "none"
                document.querySelector('#user__admin').style.display = ""
                block.style.transform += 'scaleX(2)'
                setTimeout(() => {
                    block.style.transform = 'translate(100%, 0) scaleX(1)'
                    block.classList.remove('check')
                }, 150);
            }
            else if (element.innerHTML == 'Добавить Пользователя' && isLeft != 2) {
                isLeft = 2
                document.querySelector('#product__admin').style.display = "none"
                document.querySelector('#product__add__admin').style.display = "none"
                document.querySelector('#user__add__admin').style.display = ""
                document.querySelector('#user__admin').style.display = "none"
                block.style.transform += 'scaleX(2)'
                setTimeout(() => {
                    block.style.transform = 'translate(200%, 0) scale(1)'
                    block.classList.remove('check')
                }, 150);
            }
            else if (element.innerHTML == 'Добавить Котлёл' && isLeft != 3) {
                isLeft = 3
                block.style.transform += 'scaleX(2)'
                document.querySelector('#product__admin').style.display = "none"
                document.querySelector('#product__add__admin').style.display = ""
                document.querySelector('#user__add__admin').style.display = "none"
                document.querySelector('#user__admin').style.display = "none"
                setTimeout(() => {
                    block.style.transform = 'translate(300%, 0) scale(1)'
                    block.classList.remove('check')
                }, 150);
            }


        })
    })
}
