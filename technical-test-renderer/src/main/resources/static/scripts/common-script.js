
    const removeActive =()=> {
        document.querySelectorAll('.nav-menu-items').forEach(link => {
            link.classList.remove('active');
        });
    }

    const getFilterFields = ()=> {
        return [
            document.getElementById("hidden_favorite"),
            document.getElementById("origin"),
            document.getElementById("destination"),
            document.getElementById("price")
        ];
    }

    const setStorage = ()=> {
        getFilterFields().filter(input=>input!==null)
            .forEach(input=>{
                localStorage.setItem(input.id, input.value)
            })

    }

    const getStorageAndPopulate=()=> {
        getFilterFields().filter(input=>input!==null)
            .forEach(input=>{
                input.value=localStorage.getItem(input.id)
                if (input.id==="hidden_favorite"){
                    Array.from( document.getElementsByClassName("flight-row") )
                        .forEach(row=>{
                            if (input.value && row.id!==input.value){
                                row.classList.add('hide-row')
                            }else{
                                row.classList.remove('hide-row')
                                if (input.value===row.id &&  row.lastElementChild)  {
                                    row.lastElementChild.lastElementChild.checked=true
                                }
                            }

                        })

                }
            })

    }

    const setFavorite=(radioButton)=>{
        if (radioButton) {
            const selectedValue = radioButton.value;
            const favoriteInput = document.getElementById("hidden_favorite")
            if (favoriteInput){
                let message = ''
                const flightId = favoriteInput.value;
                if (selectedValue===flightId){
                    radioButton.checked=false
                    favoriteInput.value=''
                    message = 'Vol enlevé comme favoris'

                }else{
                    favoriteInput.value = selectedValue
                    message = 'Vol ajouté comme favoris'
                }
                setStorage()
                alert(message)

            }

        }
    }

    window.onload = (event)=>{
        removeActive()
        document.querySelectorAll(".nav-menu-items").forEach((link) => {

            if ( (window.location.pathname !=="/" && link.href.includes(window.location.pathname))
                || (window.location.pathname==="/" && link.id==="home")
            ) {
                link.classList.add("active");
                link.setAttribute("aria-current", "page");
            }
        });

        getStorageAndPopulate()
        const searchForm = document.getElementById("search_form")
        if (searchForm){
            searchForm.addEventListener("submit", (event)=>{
                event.preventDefault()
                setStorage()
                searchForm.submit()
            })
        }
    }

