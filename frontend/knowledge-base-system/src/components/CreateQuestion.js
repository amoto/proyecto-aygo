
import axios from 'axios'
import { useHistory,Link } from 'react-router-dom';
import {useState, useEffect} from 'react'
import './CreateQuestion.css'


function CreateQuestion(props){

  let host = process.env.REACT_APP_HOST_IP_ADDRESS;
  const [preg, setPreguntas] = useState({title:"",description:"",tags:"", created_at:""});
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
  const handleSubmit = event => {
    event.preventDefault();
    
    axios.post(`${host}/faq/question`,preg, { headers: {
            Authorization: 'Bearer ' + idToken
        }}).then(response => console.log(response));
  };
   const handleChangeTitle = event => {
     setPreguntas({title:event.target.value,description:preg.description,tags:preg.tags, created_at:""});
  };

  const handleDescription = event => {
    setPreguntas({title:preg.title,description:event.target.value,tags:preg.tags, created_at:""});
  };
  const handleTags = event => {
    setPreguntas({title:preg.title,description:preg.description,tags:event.target.value, created_at:""});
  };
  const resetState = event =>{
    setPreguntas({title:"",description:"",tags:"", created_at:""});
  }
   return (
             <>
             <h1>Crear Pregunta</h1>
              <form onSubmit={handleSubmit} clasName="formPregunta" >
                
                <fieldset>
               <label>Titulo</label>
                  <input name="title" value={preg.title} onChange={handleChangeTitle}/>
                </fieldset>
                <br/>
                <fieldset>
                <label>Descripción</label>
                <input name="description" value={preg.description} onChange={handleDescription} />
                </fieldset>
                <br/>
                <fieldset>
                <label>Tags</label>
                <input name="tags" value={preg.tags} onChange={handleTags}/>
                </fieldset>
                <br/>
               
                <input type="submit" />
              </form>
              <button onClick={resetState}>Cancelar </button>
              <br/>
              <Link to={`/?idToken=`+window.location.search.split('=')[1]}>
                  Lista de preguntas
              </Link>
        
            </>) ;
        
};


export default CreateQuestion;