import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from 'react-router-dom';
import LandingPage from './components/views/LandingPage/LandingPage';
import RegisterPage from './components/views/RegisterPage/RegisterPage';
import LoginPage from './components/views/LoginPage/LoginPage';


function App() {
  return (
    <Router>
      <div>
        <Switch>
          <Route exact path='/' component={LandingPage} />
          {/* <LandingPage/> */}
          {/* </Route> */}
          <Route path='/register'>
            <RegisterPage/>
          </Route>
          <Route path='/login'>
            <LoginPage/>
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
