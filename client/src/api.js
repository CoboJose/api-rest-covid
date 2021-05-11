import axios from 'axios'

export default axios.create({
  baseURL: `https://api-rest-covid.herokuapp.com/v1/`
})