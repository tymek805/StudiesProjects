import PropTypes from 'prop-types';
import { useState } from 'react';

function Zadanie(props) {

    const [isDone, setIsDone] = useState(props.isDone);
    
    const done = <p className="done" onClick={()=>setIsDone(false)}>{props.name} <span style={{ color: 'green' }}>done</span></p>;
    const notDone = <p className="not-done" onClick={()=>setIsDone(true)}>{props.name} <span style={{ color: 'red' }}>not done</span></p>;

    return (isDone ? done : notDone);
}

Zadanie.propTypes = {
    name: PropTypes.string,
    isDone: PropTypes.bool,
};

Zadanie.defaultProps = {
    name: "Zadanie",
    isDone: false,
};

export default Zadanie;

