import { Link, useParams } from "react-router-dom";
import {useState, useEffect} from 'react'
import axios from 'axios'
import './QuestionDetail.css';


function QuestionDetail(props){
    let idToken = ''
    const getIdToken = () => {
        if (props.idToken) {
            idToken = props.idToken
        } else {
            console.log(window.location)
            idToken = window.location.search.split('=')[1]
        }

        return idToken
    }
    useEffect(() => {
        getIdToken();
    })
    let host = process.env.REACT_APP_HOST_IP_ADDRESS;

    const [preg, setPregunta] = useState({
        "id": 0,
        "title": "",
        "description": "",
        "tags": "",
        "created_at": "",
        "created_by": "",
        "votes_up": 0,
        "votes_down": 0
    });
    
    const [respuesta, setRespuesta] = useState([{text:"",up_votes:"",down_votes:""}]);
    const [respuestas, setRespuestas] = useState([{}]);
    let {id} = useParams();

    const createResponse = event => {
        console.log(idToken)
        console.log(getIdToken())

        axios.post(`${host}/faq/response/question/${id}`,respuesta, { headers: {
                Authorization: 'Bearer ' + idToken
            }}).then(response => console.log(response.data));
      };

      const handleTestResponse = event => {    
        setRespuesta({text:event.target.value,up_votes:"",down_votes:""});
      };

    useEffect(() =>{
        getIdToken()
        setRespuestas([{}])
        setPregunta({
            "id": 0,
            "title": "",
            "description": "",
            "tags": "",
            "created_at": "",
            "created_by": "",
            "votes_up": 0,
            "votes_down": 0
        })
        
        axios.get(`${host}/faq/question/${id}`, { headers: {
                Authorization: 'Bearer ' + idToken
            }}).then(response => setPregunta(response.data));
        axios.get(`${host}/faq/responses/question/${id}`, { headers: {
                Authorization: 'Bearer ' + idToken
            }}).then(response => setRespuestas(response.data));
   },[]);
    return(
        <div>   
            <h1>{preg.title}</h1>
            <h2>Respuestas</h2>
           
            {respuestas.map(r => <div className="response">{r.text}</div>)}
            <br/>
            <div >  
                 <label>Respuesta</label>
                 <input name="text" value={respuesta.text} onChange={handleTestResponse}/>
                <br/>
                <button onClick={createResponse}>Crear Respuesta</button>
            </div>
            <br/>
            <Link to={`/?idToken=`+window.location.search.split('=')[1]} >
                    Lista de preguntas
             </Link>
             <br/>
             <Link to={`/CreateQuestion?idToken=`+window.location.search.split('=')[1]} >
                    Crear Pregunta
             </Link>


        </div>
    )
}



export default QuestionDetail;