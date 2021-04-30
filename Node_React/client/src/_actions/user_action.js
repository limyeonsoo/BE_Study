import axios from 'axios';
import {
    LOGIN_USER
} from './types';

export default function loginUser(dataToSubmit){
    const request = axios.post('/api/user/login', dataToSubmit)
        .then(res => res.data);
    
    return{
        type:LOGIN_USER,
        payload: request
    }
}