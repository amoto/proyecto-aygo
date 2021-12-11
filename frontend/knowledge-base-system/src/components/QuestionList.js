import { Link } from "react-router-dom";
import axios from 'axios'
import {useState, useEffect} from 'react'
import './QuestionList.css';





function QuestionList(props){
  
    const [preg, setPreguntas] = useState([]);
    let host = process.env.REACT_APP_HOST_IP_ADDRESS;
    useEffect(() =>{
         axios.get(`${host}/faq/questions`, { headers: {
                 Authorization: 'Bearer ' + props.idToken
             }}).then(response => setPreguntas(response.data));
    },[]);
    return(
        <>
           {preg.map(r => <Question id={r.id} key={r.id}
                            title={r.title} 
                            description={r.description} 
                            createAt={r.created_at} 
                            createBy={r.created_by}
                            tags={r.tags}
                            idToken={props.idToken}
                            
                            responses={r.reponses}/>)} 
                            <br/>
                            <Link
                                to={`/CreateQuestion?idToken=` + props.idToken}
                             >
                                 CreateQuestion
                                 </Link>
                                
        </>

    );


}


function Question({id,title,description,createAt,createBy,tags,responses, idToken}){
    const listq = responses;
    
     return (
         
            <div className="Questionlist" key={id}>
            
            <h1>{title}</h1>
            <br/>
            {createAt}
            <Link
            to={`/QuestionDetail/${id}?idToken=`+ idToken}
            key={id}
              >
             <button>Detail</button>
          </Link>

            </div>


         
     );
}


export default QuestionList;