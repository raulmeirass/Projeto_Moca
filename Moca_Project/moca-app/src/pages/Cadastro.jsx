import api from "../api.js";
import Header from "../components/Header";
import vetorCadastro1 from "../assets/img/vetorCadastro1.png";
import vetorCadastro2 from "../assets/img/vetorCadastro2.png";
import "../assets/css/style.css";
import { useState } from "react";
import { useNavigate } from "react-router";

function Cadastro() {

    // Atributos
    const [nomeValue, setNomeValue] = useState('');
    const [emailValue, setEmailValue] = useState('');
    const [telefoneValue, setTelefoneValue] = useState('');
    const [senhaValue, setSenhaValue] = useState('');
    const [confirmeSenhaValue, setConfirmeSenhaValue] = useState('');
    const navigate = useNavigate();
    // Consts para exibir frases de alertas
    const [nomeAlert, setNomeAlert] = useState('');
    const [emailAlert, setEmailAlert] = useState('');
    const [emailAlertExist, setEmailAlertExist] = useState('');
    const [telefoneAlert, setTelefoneAlert] = useState('');
    const [senhaAlert, setSenhaAlert] = useState('');
    const [confirmeSenhaAlert, setConfirmeSenhaAlert] = useState('');

    // Função chamada no botão para cadastrar o usuário
    function postCadastro() {

        // Regex
        const regexEmail = /\S+@\S+\.\S+/;
        const telefoneRegex = /^\d{11}$/;
        const regexNumbers = /\d/;


        // Fazendo as validações dos inputs e cadastrando
        setNomeAlert(nomeValue.trim() === '' || regexNumbers.test(nomeValue) ? 'Digite um nome e sem números' : '');
        setEmailAlert(!regexEmail.test(emailValue) ? 'Digite um email válido' : '');
        setTelefoneAlert(!telefoneRegex.test(telefoneValue) ? 'Telefone inválido, utilize 11 digitos! EX: DD912344321' : '');
        setSenhaAlert(senhaValue.trim() === '' || senhaValue.length < 6 ? 'Senha inválida! Mínimo de 6 caracteres.' : '');
        setConfirmeSenhaAlert(confirmeSenhaValue !== senhaValue ? 'Senhas não conferem!' : '');

        if (nomeValue.trim() !== '' && !regexNumbers.test(nomeValue) && regexEmail.test(emailValue) && telefoneRegex.test(telefoneValue) && senhaValue.trim() !== '' && senhaValue.length >= 6 && confirmeSenhaValue === senhaValue) {
            // Chamando o axios para criar um cliente = usuario
            // e passando um json com o valor dos inputs
            api.post("usuarios/cadastrar/", {
                nome: nomeValue,
                email: emailValue,
                senha: senhaValue,
                telefone: telefoneValue,
                idTipoPerfil: 5,
            }).then(() => {
                navigate('/login');
                setEmailAlertExist('');
            }).catch((err) => {
                console.error(err.response.data.statusCode);
                if (err.response.status === 409) {
                    setEmailAlertExist('Email ja existente');
                }
            });
        }

    }

    function handleFormSubmit(event) {
        event.preventDefault(); // Impede o comportamento padrão de recarregar a página

        // Chamando a função postLogin()
        postCadastro();
    }


    // Retornando a estrutura do site de Cadastro
    return (
        <div className="cad-log">
            <Header />
            <div className="texto-cadastro">
                <p >Pronto para se organizar?</p>
                <h2>Quando você se organiza, consegue mais tempo para fazer as coisas que realmente importam.</h2>
            </div>

            <section className="container-cadastro">
                <img className="bolaAzulCadastro" src={vetorCadastro1} alt="" />
                <div className="card-cadastro">
                    <h1>Cadastre-se</h1>
                    <form className="cont-cadastro" onSubmit={handleFormSubmit}>
                        <div className="input-cadastro">
                            <input type="text" onChange={(event) => setNomeValue(event.target.value)} placeholder="Nome completo" />
                            <small>{nomeAlert}</small>
                        </div>
                        <div className="input-cadastro">
                            <input type="text" onChange={(event) => setEmailValue(event.target.value)} placeholder="Email" />
                            <small>{emailAlert}</small>
                            <small>{emailAlertExist}</small>
                        </div>
                        <div className="input-cadastro">
                            <input type="text" onChange={(event) => setTelefoneValue(event.target.value)} placeholder="Telefone" />
                            <small>{telefoneAlert}</small>
                        </div>
                        <div className="input-cadastro">
                            <input type="password" onChange={(event) => setSenhaValue(event.target.value)} placeholder="Senha" />
                            <small>{senhaAlert}</small>
                        </div>
                        <div className="input-cadastro">
                            <input type="password" onChange={(event) => setConfirmeSenhaValue(event.target.value)} placeholder="Confirmar senha" />
                            <small>{confirmeSenhaAlert}</small>
                        </div>
                        <div className="input-cadastro">
                            <div className="btn-cadastro"><button type="submit">Cadastrar</button></div>
                        </div>
                    </form>
                </div>
                <img className="bolaPretaCadastro" src={vetorCadastro2} alt="" />

            </section>
        </div>
    );
}

export default Cadastro;