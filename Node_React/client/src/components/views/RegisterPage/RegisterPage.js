import axios from 'axios';
import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import { registerUser } from '../../../_actions/user_action';

function RegisterPage(props) {

    const dispatch = useDispatch();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const onEmailHandler = (e) => {
        setEmail(e.currentTarget.value);
    }
    const onNameHandler = (e) => {
        setName(e.currentTarget.value);
    }
    const onConfirmPasswordHandler = (e) => {
        setConfirmPassword(e.currentTarget.value);
    }
    const onPasswordHandler = (e) => {
        setPassword(e.currentTarget.value);
    }
    const onSubmitHandler = (e) => {
        e.preventDefault();

        if(password !== confirmPassword){
            return alert('Check your PW');
        }

        let body={
            name: name,
            email: email,
            password: password
        }

        dispatch(registerUser(body))
            .then(res => {
                console.log(res);
                if(res.payload.success){
                    console.log(props.history);
                    props.history.push('/login');
                }else{
                    alert('failed to sign up');
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

                <label>Name</label>
                <input type='name' value={name} onChange={onNameHandler} />

                <label>Password</label>
                <input type='password' value={password} onChange={onPasswordHandler} />
                
                <label>Confirm Password</label>
                <input type='password' value={confirmPassword} onChange={onConfirmPasswordHandler} />

                <br/>
                <button>
                    Sign Up
                </button>
            </form>
        </div>
    )
}

export default RegisterPage;
