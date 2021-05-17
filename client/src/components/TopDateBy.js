import { Switch } from '@material-ui/core';
import React, { useEffect, useState } from 'react'
import axios from "../api"
import "../style/TopDateBy.css"
function TopDateBy({topTitle, setTopTitle}) {
    const [topDateBy, setTopDateBy] = useState({});
    const [loading, setLoading] = useState(true);
    useEffect(()=>{
        const getTopDateBy = () => {
            
            const title = (title) => {
                switch(title){
                    case "Nuevos confirmados":
                        return "Confirmed";
                    case "Nuevas fallecimientos":
                        return"Deaths";
                    default:
                        return"Confirmed";
                }
            }

            axios.get("/topDateBy?by="+title(topTitle))
            .then((res) => {
                console.log(res.data)
                setTopDateBy(res.data)
                setLoading(false)
            })
            .catch((err) => {
                console.log(err);
            })
        }
        getTopDateBy();
    }, [topTitle]);
    
    const handleChange = (e) =>{
        if(!e.target.checked){
            setTopTitle("Nuevos confirmados");
            setLoading(true);
        }else{
            setTopTitle("Nuevos fallecimientos");
            setLoading(true);
        }
    }

    return (
        <div className="topDateBy-container">
           {loading ? (<><br/><br/></>) : (
               <>
                    <p>La fecha donde hubo mayor número de <strong>{topTitle}</strong> fue:</p>
                    <h5>{topDateBy.date}</h5>
                    <p>Con un total de {topTitle == "Nuevos confirmados" ? <strong>{topDateBy.newConfirmed}</strong> : <strong>{topDateBy.newDeaths}</strong>} personas</p>
                </>
           )}
            <div className="switch-container">
                <p>Nuevos confirmados</p>
                <Switch
                onChange={handleChange}
                />
            <p>Nuevos fallecimientos</p>
            </div>
            
        </div>
    )
}

export default TopDateBy
