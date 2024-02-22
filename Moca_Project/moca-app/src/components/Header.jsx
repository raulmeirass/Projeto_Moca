import { useNavigate } from "react-router";
import logo from "../assets/img/logoMoca.png";

function Header() {
  
  const navigate = useNavigate();

  // Função para o menu hamburguer funcionar
  function getMenu() {
    const dropDownMenu = document.querySelector('.dropdown_menu');
    dropDownMenu.classList.toggle('open');

    // const idUsuario = localStorage.getItem("id");
    // const tokenUsuario = localStorage.getItem("token");
    // console.log(idUsuario, tokenUsuario);
  }

  // Retornando a estrutura do component Header
  return (
    <header id="header" className="col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
      <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,700,0,0" />
      <nav>
        <a className="text-none" href="/">
          <div id="logo">
            <img src={logo} alt="MOCA" />
            <h1><b>moca</b></h1>
          </div>
        </a>
        <ul className="navbar">
          <li><a href="/">Início</a></li>
          <li><a href="/#dica">Dicas</a></li>
          <li><a href="/#funcionalidade-sistema">Funcionalidades</a></li>
          <li><a href="/#footer">Contato</a></li>
        </ul>
      </nav>
      <div id="btns">
        <button className="entrar" onClick={() => navigate('/login')}>LOGIN</button>
        <button className="cadastrar" onClick={() => navigate('/cadastro')}>CADASTRAR</button>
        <div className="mobile-menu">
          <span className="material-symbols-outlined" onClick={getMenu}>menu</span>
        </div>
      </div>

      <div className="dropdown_menu">
        <li><a href="/">Início</a></li>
        <li><a href="/#dica">Dicas</a></li>
        <li><a href="/#funcionalidade-sistema">Funcionalidades</a></li>
        <li><a href="/#footer">Contato</a></li>
        <li><button onClick={() => navigate('/login')}>LOGIN</button></li>
        <li><button onClick={() => navigate('/cadastro')}>CADASTRAR</button></li>
      </div>
    </header>
  );
}

export default Header;