

export const armazenar = (chave,valor) => {
    localStorage.setItem(chave,valor);
}

export const consultar = (chave) => {
    localStorage.getItem(chave);
}

export const apagar = (chave) => {
    localStorage.removeItem(chave);
}