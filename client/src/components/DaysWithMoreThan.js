import { Button, FormControl, Input, MenuItem, Select } from '@material-ui/core'
import axios from "../api"
import React, { useEffect, useState } from 'react'
import "../style/DaysWithMoreThan.css"

function DaysWithMoreThan() {
    const [input, setInput] = useState({
        type: "Confirmed",
        number: 500
    });
    const [data, setData] = useState(null);
    const [result, setResult] = useState({});

    useEffect(() => {
        const getInitialData = () =>{
            axios.get("DaysWithMoreThan?by=Confirmed&more=500")
            .then((res)=>{
                setData(res.data);
                setResult({
                    type: input.type,
                    number: input.number
                });
            })
            .then(err => {
                console.log(err)
            })
        }
        getInitialData();
    }, [])

    const handleChange = (e) => {
        setInput({
            ...input,
            [e.target.name]: e.target.value
        });
    }

    const handleSubmit = () => {
      console.log(input, "entra aquí");
        axios.get("DaysWithMoreThan?by="+input.type+"&more="+input.number)
        .then((res)=>{
            setData(res.data);
            setResult({
                type: input.type,
                number: input.number
            });
        })
    }

    const typeName = (type) => {
        switch(type){
            case "Confirmed":
                return "nuevos confirmados";
            case "Deaths":
                return "nuevas muertes";
            case "Icu":
                return "personas en UCI"
            case "Recovered":
                return "nuevos recuperados";
            case "Hospitalised":
                return "nuevos hospitalizados"
            default:
                return "nuevos Confirmados";
        }
    }

    return (
        <>
            <h3 style={{paddingTop: "20px", paddingBottom:"20px"}}>Días con más de {result.number} personas para {typeName(result.type)}</h3>
            <div className="daysWithMoreThan-container">
                <form className="form-container">
                    <FormControl>
                        <Select
                        id="demo-simple-select"
                        name="type"
                        value={input.type}
                        onChange={handleChange}
                        >
                            <MenuItem value="Confirmed">Nuevos confirmados</MenuItem>
                            <MenuItem value="Deaths">Nuevas muertes</MenuItem>
                            <MenuItem value="Icu">En UCI</MenuItem>
                            <MenuItem value="Recovered">Nuevos recuperados</MenuItem>
                            <MenuItem value="Hospitalised">Nuevos hospitalizados</MenuItem>
                        </Select>
                    </FormControl>
                    <FormControl>
                    <Input type="number" name="number" value={input.number} onChange={handleChange}/>
                    </FormControl>
                    <Button onClick={handleSubmit}>Enviar</Button>
                </form>
                {!data ? "" : (
                    <>
                        <p style={{textAlign:"center", paddingTop: "20px", paddingBottom:"20px"}}>Hubo <strong>{data}</strong> días con más de {result.number} personas para {typeName(result.type)}</p>
                    </>
                )}

            </div>
        </>
    )
}

export default DaysWithMoreThan
