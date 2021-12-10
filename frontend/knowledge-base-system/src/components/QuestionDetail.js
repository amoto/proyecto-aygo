import { Link, useParams } from "react-router-dom";
import {useState, useEffect} from 'react'
import axios from 'axios'
import './QuestionDetail.css';


function QuestionDetail(){
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
    
        axios.post(`${host}/faq/response/question/${id}`,respuesta).then(response => console.log(response.data));       
      };

      const handleTestResponse = event => {    
        setRespuesta({text:event.target.value,up_votes:"",down_votes:""});
      };

    useEffect(() =>{
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
        
        axios.get(`${host}/faq/question/${id}`).then(response => setPregunta(response.data));
        axios.get(`${host}/faq/responses/question/${id}`).then(response => setRespuestas(response.data));      
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
            <Link to={`/`} >
                    Lista de preguntas
             </Link>
             <br/>
             <Link to={`/CreateQuestion`} >
                    Crear Pregunta
             </Link>


        </div>
    )
}



export default QuestionDetail;