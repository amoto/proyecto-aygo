import logo from './logo.svg';
import './App.css';
import React, {useState, useEffect} from 'react';
import CreateQuestion from './components/CreateQuestion';
import QuestionList from './components/QuestionList';


function App() {
    const [idToken, setIdToken] = useState('');
    const cognito_domain = process.env.COGNITO_DOMAIN;
    const cognito_client_id = process.env.COGNITO_CLIENT_ID;
    const redirect_url = process.env.COGNITO_REDIRECT_URL;
    console.log(process.env)

    useEffect(() => {
        getIdToken();
    }, [idToken]);


    const getIdToken = () => {
        const hash = window.location.hash.substr(1);
        const objects = hash.split("&");
        objects.forEach(object => {
            const keyVal = object.split("=");
            if (keyVal[0] === "id_token") {
                setIdToken(keyVal[1]);
            }
        });
        console.log(window.location)
        if (idToken.length > 0 && window.location.search.split('=').length > 1) {
            setIdToken(window.location.search.split('=')[1])
        }

        return idToken
    };

    return (
        <div className="App">
            {idToken.length > 0 ?
                (
                    <QuestionList idToken = {idToken}/>
                ) : (
                    <a
                        href={`https://${cognito_domain}/login?response_type=token&client_id=${cognito_client_id}&redirect_uri=${redirect_url}`}
                        color="primary"
                        className="mt-5 float-center"
                    >
                        Log In
                    </a>
                )
            }
        </div>
    );
}

export default App;
