import { useEffect, useState } from "react";
import './assets/Splash.css';
import cloverLogo from './assets/PTCC.png';

const SplashScreen = () => {
    const [showSplash, setShowSplash] = useState(true);
    const [splashDisplay, setSplashDisplay] = useState({ display: 'grid' });

    useEffect(() => {
        setTimeout(() => {
            setShowSplash(false);
            setTimeout(() => {
                setSplashDisplay({ display: 'none' });
            }, 300); // Tempo para a animação de fade-out
        }, 300); // Tempo para exibir o splash
    }, []);

    const splashClasses = showSplash ? 'SplashScreen fade-in' : 'SplashScreen fade-out';

    return (
        <div className={splashClasses} style={{ display: splashDisplay.display }}>
            <article>
                <div className="titleSplash">
                    <h1>Clover</h1>
                    <img src={cloverLogo} alt="Clover" />
                </div>
            </article>
        </div>
    );
}

export default SplashScreen;
