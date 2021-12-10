import { Link } from "react-router-dom";
import axios from 'axios'
import {useState, useEffect} from 'react'
import './QuestionList.css';





function QuestionList(){
  
    const [preg, setPreguntas] = useState([]);
    console.log('envaara'+process.env.REACT_APP_HOST_IP_ADDRESS);
    let host = process.env.REACT_APP_HOST_IP_ADDRESS;
    useEffect(() =>{
         axios.get(`${host}/faq/questions`).then(response => setPreguntas(response.data));
    },[]);
    return(
        <>
           {preg.map(r => <Question id={r.id} key={r.id}
                            title={r.title} 
                            description={r.description} 
                            createAt={r.created_at} 
                            createBy={r.created_by}
                            tags={r.tags}

                            
                            responses={r.reponses}/>)} 
                            <br/>
                            <Link
                                to={`/CreateQuestion`}    
                             >
                                 CreateQuestion
                                 </Link>
                                
        </>

    );


}


function Question({id,title,description,createAt,createBy,tags,responses}){
    const listq = responses;
    
     return (
         
            <div className="Questionlist" key={id}>
            
            <h1>{title}</h1>
            <br/>
            {createAt}
            <Link
            to={`/QuestionDetail/${id}`}
            key={id}
              >
             <button>Detail</button>
          </Link>

            </div>


         
     );
}


export default QuestionList;