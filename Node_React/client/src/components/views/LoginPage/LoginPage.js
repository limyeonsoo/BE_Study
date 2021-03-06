import axios from 'axios';
import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import { loginUser } from '../../../_actions/user_action';
import {withRouter} from 'react-router-dom';

const LoginPage = (props) => {
    const dispatch = useDispatch();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const onEmailHandler = (e) => {
        setEmail(e.currentTarget.value);
    }
    const onPasswordHandler = (e) => {
        setPassword(e.currentTarget.value);
    }
    const onSubmitHandler = (e) => {
        e.preventDefault();

        let body={
            email: email,
            password: password
        }

        

        dispatch(loginUser(body))
            .then(res => {
                if(res.payload.loginSuccess){
                    //console.log(props);
                    //console.log(props.history);
                    props.history.push('/')

                }else{
                    alert('error');
                }
            })
    }

    return (
        <div style={{
            display: 'flex', justifyContent: 'center', alignItems: 'center', width: '100%', height: '100vh'
        }}>

            <form style={{ display:'flex', flexDirection: 'column'}} onSubmit={onSubmitHandler}>
                <label>Email</label>
                <input type='email' value={email} onChange={onEmailHandler} />
                <label>Password</label>
                <input type='password' value={password} onChange={onPasswordHandler} />

                <br/>
                <button>
                    Login
                </button>
            </form>
        </div>
    )
}

export default withRouter(LoginPage);