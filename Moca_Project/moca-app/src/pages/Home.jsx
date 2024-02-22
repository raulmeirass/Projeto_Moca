import Header from "../components/Header";
// Importando as imagens da tela
import BannerInicio from "../assets/img/banner-inicio.png";
import CelularesDicas from "../assets/img/celulares-dicas.png";
import CelularApp from "../assets/img/cell.png";
import PlaystoreApplestore from "../assets/img/app-playstore-applestore-removebg.png";
import Logo from "../assets/img/logoMoca.png";
import Instagram from "../assets/img/instagram.png";
import Twitter from "../assets/img/twitter.png";
import Facebook from "../assets/img/facebook.png";
import Whatsapp from "../assets/img/whatsapp.png";
import HomemCartao from "../assets/img/homemCartao.png";
import "../assets/css/style.css";
import { useNavigate } from "react-router";

function Home() {

    const navigate = useNavigate();

    if (localStorage.getItem("id") === null) {
        localStorage.setItem("id", "");
        localStorage.setItem("nome", "");
        localStorage.setItem("token", "");
    }
    // Retornando a estrutura do site e o Header
    return (
        <div>
            <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,700,0,0" />
            <Header />
            <main className="col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                <section id="inicio" className="intro-scroll introducao col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                    <div className="intro-frase col-xxl-6 col-xl-5 col-lg-12 col-md-12 col-sm-12 col-es-12">
                        <h1><b>Tenha suas finanças sempre organizadas de qualquer lugar</b></h1>
                        <div className="frase1">
                            <p>Com o nosso app de organização financeira, controle suas despesas e receitas de forma simples e eficiente, para alcançar seus objetivos financeiros sem stress.</p>
                            <button className="cadastrar" onClick={() => navigate('/cadastro')}>Cadastro</button>
                        </div>
                        <div className="dados">
                            <div>
                                <span className="material-symbols-outlined">thumb_up</span>
                                <h4><b>+</b> 50K</h4>
                                <p>Clientes satisfeitos</p>
                            </div>
                            <div>
                                <span className="material-symbols-outlined">database</span>
                                <h4><b>+</b> 10M</h4>
                                <p>Patrimônimo cadastrado</p>
                            </div>
                        </div>
                    </div>
                    <div className="pc-cel-dash col-xxl-6 col-xl-6">
                        <img src={BannerInicio} alt="Dashboard MOCA" />
                    </div>
                </section>

                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 170">
                    <path
                        fill="#222940"
                        fillOpacity="1"
                        d="M0,160L120,138.7C240,117,480,75,720,53.3C960,32,1200,32,1320,32L1440,32L1440,320L1320,320C1200,320,960,320,720,320C480,320,240,320,120,320L0,320Z"
                    ></path>
                </svg>

                <section id="dica" className="dicas col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                    <div className="col-xxl-4 col-xl-4">
                        <img src={CelularesDicas} alt="App MOCA" />
                    </div>
                    <div className="dicas-numeros col-xxl-8 col-xl-8 col-lg-12 col-md-12 col-sm-12 col-es-12 intro-scroll">
                        <h1>Tenha controle do seu patrimônio</h1>
                        <div className="numeracao">
                            <div>
                                <div className="ordem">1</div>
                                <h4><b>Cadastre suas contas e cartões em um só lugar</b></h4>
                                <p>Mantenha todas as suas finanças organizadas com a facilidade de ter suas contas e cartões
                                    cadastrados em um único lugar.</p>
                            </div>
                            <div>
                                <div className="ordem">2</div>
                                <h4><b>Registre todas as suas despesas em tempo real</b></h4>
                                <p>Tenha uma previsão poderosa do seu fluxo de caixa e acompanhe seus gastos em tempo real, de
                                    qualquer lugar.</p>
                            </div>
                            <div>
                                <div className="ordem">3</div>
                                <h4><b>Controle cada centavo do seu dinheiro</b></h4>
                                <p>Tenha uma visão completa das suas finanças informando sua renda e ganhos extras, garantindo
                                    um ponto de partida para suas metas financeiras</p>
                            </div>
                            <div>
                                <div className="ordem">4</div>
                                <h4><b>Transforme a gestão financeira em um hábito</b></h4>
                                <p>Lance seus gastos do dia a dia, acompanhe relatórios e assuma o controle do seu dinheiro com
                                    nossa ferramenta intuitiva e fácil de usar</p>
                            </div>
                        </div>
                    </div>
                </section>

                <section id="funcionalidade-sistema" className="funcionalidades col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                    <h1 className="intro-scroll"><b>Funcionalidades</b></h1>
                    <h2 className="intro-scroll">As funcionalidades de um produto são o que o tornam útil, mas nossa simplicidade é o que torna a Moca amada pelos usuários</h2>
                    <div className="card-foto col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12 intro-scroll">
                        <div className="func col-xxl-5 col-xl-6">
                            <div className="cards-func">
                                <h3>Alertas</h3>
                                <p>Receba alertas em tempo real para manter o controle de seus gastos.</p>
                            </div>
                            <div className="cards-func">
                                <h3>Criação de categorias</h3>
                                <p>Personalize as categorias para atender às suas necessidades específicas.</p>
                            </div>
                            <div className="cards-func">
                                <h3>Notificações</h3>
                                <p>Receba notificações SMS para ficar em dia com sua gestão.</p>
                            </div>
                            <div className="cards-func">
                                <h3>Controle de cartões</h3>
                                <p>Controle todos seus cartões em um único lugar.</p>
                            </div>
                            <div className="cards-func">
                                <h3>Relatórios</h3>
                                <p>Resumos incríveis, com gráficos simples e completos.</p>
                            </div>
                            <div className="cards-func">
                                <h3>Calculo de Investimentos</h3>
                                <p>Tenha uma estimativa de seus rendimentos utilizando a calculadora.</p>
                            </div>
                        </div>
                        <div>
                            <img src={HomemCartao} alt="Atingindo Metas" />
                        </div>
                    </div>
                </section>

                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
                    <path
                        fill="#EFEFEF"
                        fillOpacity="1"
                        d="M0,32L120,53.3C240,75,480,117,720,122.7C960,128,1200,96,1320,80L1440,64L1440,0L1320,0C1200,0,960,0,720,0C480,0,240,0,120,0L0,0Z"
                    />
                </svg>

                <section className="banner-app col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 intro-scroll">
                    <div className="card-app col-xxl-7 col-xl-7 col-lg-8 col-md-10 col-sm-10 col-es-10">
                        <div className="frase-app col-sm-12 col-es-12">
                            <h1>
                                <b>Baixe agora e não perca mais tempo com planilhas complexas</b>
                            </h1>
                            <img src={PlaystoreApplestore} alt="App Moca" />
                        </div>
                        <div className="cel-frase">
                            <img src={CelularApp} alt="Celular com App MOCA" />
                        </div>
                    </div>
                </section>
            </main>
            <footer id="footer" className="rodape col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                <div className="footer-menu col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                    <div className="footer-logo">
                        <img src={Logo} alt="MOCA" />
                        <h1>moca</h1>
                    </div>
                    <div className="footer-nav">
                        <ul>
                            <li><a href="#inicio">Início</a></li>
                            <li><a href="#dica">Dicas</a></li>
                            <li><a href="#funcionalidade-sistema">Funcionalidades</a></li>
                        </ul>
                    </div>
                    <div className="footer-btn">
                        <button className="entrar" onClick={() => navigate('/login')}>LOGIN</button>
                        <button className="cadastrar" onClick={() => navigate('/cadastro')}>CADASTRAR</button>
                    </div>
                </div>
                <hr />
                <div className="contato col-xxl-12 col-xl-12 col-lg-12 col-md-12 col-sm-12 col-es-12">
                    <p>© Moca, 2023.</p>
                    <div className="rede-social">
                        <p>Contatos:</p>
                        <a href="/"><img src={Instagram} alt="Instagram MOCA" /></a>
                        <a href="/"><img src={Twitter} alt="Twitter MOCA" /></a>
                        <a href="/"><img src={Facebook} alt="Facebook MOCA" /></a>
                        <a href="/"><img src={Whatsapp} alt="Whatsapp MOCA" /></a>
                    </div>
                </div>
            </footer>
        </div>
    );
}

export default Home;