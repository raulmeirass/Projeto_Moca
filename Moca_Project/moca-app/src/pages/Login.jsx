import api from "../api.js";
import Header from "../components/Header";
import bolaAzul from "../assets/img/Vector (5).png";
import bolaPreta from "../assets/img/Vector (4).png";
import { armazenar } from "../LocalStorages";
import "../assets/css/style.css";
import { FaSpinner } from "react-icons/fa";
import { useState } from "react";
import { useNavigate } from "react-router";

function Login() {

    // Atributos
    const [clicou, setClicou] = useState(false);
    const [emailValue, setEmailValue] = useState('');
    const [senhaValue, setSenhaValue] = useState('');
    const [emailAlert, setEmailAlert] = useState('');
    const [emailAlertNotExtist, setEmailAlertNotExist] = useState('');
    const [alertCredenciais, setAlertCredenciais] = useState('');
    const [senhaAlert, setSenhaAlert] = useState('');
    const navigate = useNavigate();
    const styles = {
        esconder: { display: 'none' },
        mostrar: { display: 'block' }
    };

    // Fução para logar o usuário
    function postLogin() {

        const regexEmail = /\S+@\S+\.\S+/;

        setEmailAlert(emailValue.trim() === '' ? 'Digite o email' : '');
        setSenhaAlert(senhaValue.length < 6 || senhaValue.trim() === '' ? 'Digite a senha' : '');

        if (emailValue.trim() !== '' && senhaValue.length >= 6 && senhaValue.trim() !== '') {
            setClicou(true);
            api.post("usuarios/login", {
                email: emailValue,
                senha: senhaValue
            }).then((response) => {
                console.log(response);
                armazenar("nome", response.data.nome);
                armazenar("id", response.data.id);
                armazenar("token", response.data.token);
                navigate("/dashboard");
                setEmailAlertNotExist('');
            }).catch((err) => {
                setClicou(false);
                console.error(err.response.data)
                if (err.response.status === 404) {
                    setEmailAlertNotExist("Email do usuário não cadastrado!");
                }
                if (err.response.status === 403) {
                    setAlertCredenciais('Email ou senha incorreto');
                }
            });
        }
    }

    function handleFormSubmit(event) {
        event.preventDefault(); // Impede o comportamento padrão de recarregar a página

        // Chamando a função postLogin()
        postLogin();
    }

    // Return do HTML
    return (
        <div>
            <Header />
            <div className="texto-login">
                <p>Hora de se organizar</p>
                <h2>Controle suas despesas e receitas de forma simples e eficiente.</h2>
            </div>
            <section className="container-login">
                <img className="bolaAzul" src={bolaAzul} alt="" />
                <div className="card-login">
                    <h1>Login</h1>
                    <form className="cont-login" onSubmit={handleFormSubmit}>
                        <div className="input-login">
                            <input type="text" onChange={(event) => setEmailValue(event.target.value)} placeholder="Email" />
                            <small>{emailAlert}</small>
                            <small>{emailAlertNotExtist}</small>
                        </div>
                        <div className="input-login">
                            <input type="password" onChange={(event) => setSenhaValue(event.target.value)} placeholder="Senha" />
                            <small>{senhaAlert}</small>
                            <small>{alertCredenciais}</small>
                        </div>
                        <div className="input-login">
                            <div><FaSpinner className="spinner" style={clicou ? styles.mostrar : styles.esconder} /></div>
                            <button type="submit"
                                style={clicou ? styles.esconder : styles.mostrar}
                                className="btn-login"
                            >Login</button>
                        </div>
                    </form>
                </div>
                <img className="bolaPreta" src={bolaPreta} alt="" />
            </section>
        </div>
    );
}

export default Login;