import axios from 'axios';
import React, {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {auth} from '../_actions/user_action';

export default function (SpecificComponent, option, adminRoute = null){

    //option : 
    // null => all user
    // true => loggined
    // false => not loggined

    function AuthenticationCheck(props){

        const dispatch = useDispatch();

        useEffect(() => {
        
            dispatch(auth())
                .then(res => {
                    console.log(res);
                    // // Not Loggin
                    if(!res.payload.isAuth){
                        if(option){ // option : true  ->  X
                            props.history.push('/login');
                        }
                    }else{ // loggined
                        if(adminRoute && !res.payload.isAdmin){
                            props.history.push('/');
                        }else{
                            // logged in  -> login & register X
                            if(option === false)
                                props.history.push('/');
                        }
                    }
                })

        },[]);
    
        return(
            <SpecificComponent />
        )
    }


    return AuthenticationCheck;
}