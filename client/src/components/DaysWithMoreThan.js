import { Button, FormControl, Input, MenuItem, Select } from '@material-ui/core'
import axios from "../api"
import React, { useEffect, useState } from 'react'
import "../style/DaysWithMoreThan.css"

function DaysWithMoreThan() {
    const [input, setInput] = useState({
        type: "Confirmed",
        number: 500
    })
    const [data, setData] = useState({})

    useEffect(() => {
        const getInitialData = () =>{
            axios.get("DaysWithMoreThan?by="+input.type+"&more="+input.number)
            .then((res)=>{
                setData(res.data)
            })
            .then(err => {
                console.log(err)
            })
        }
        getInitialData();
    }, [])

    const handleChange = (e) => {
        console.log(e)
        setInput({
            ...input,
            [e.target.name]: e.target.value
        });
    }

    const handleSubmit = () => {
      console.log(input, "entra aquí");
        axios.get("DaysWithMoreThan?by="+input.type+"&more="+input.number)
        .then((res)=>{
            setData(res.data)
        })
    }

    const typeName = (type) => {
        switch(type){
            case "Confirmed":
                return "Nuevos confirmados";
            case "Deaths":
                return "Nuevas muertes";
            case "Icu":
                return "personas en UCI"
            case "Recovered":
                return "Nuevos recuperados";
            case "Hospitalised":
                return "Nuevos hospitalizados"
            default:
                return "Nuevos Confirmados";
        }
    }

    return (
        <>
        <h3 style={{marginBottom:"10px"}}>Días con más de {input.numeber} para {input.type}</h3>
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
                    <p>Hubo X días para el umbral de {input.number} personas para {typeName(input.type)}</p>
                </>
            )}

        </div>
        </>
    )
}

export default DaysWithMoreThan
