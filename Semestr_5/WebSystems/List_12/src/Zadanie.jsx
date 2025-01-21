import PropTypes from 'prop-types';

function Zadanie(props) {

    const status = props.isDone 
        ? <span className="done" style={{ cursor: 'pointer', color: 'green' }}>done</span> 
        : <span className="not-done" style={{ cursor: 'pointer', color: 'red' }}>not done</span>;

    return (
        <tr>
            <td>{props.name}</td>
            <td>{props.author}</td>
            <td>{status}</td>
            <td>{props.points}</td>
            
        </tr>
    );
}

Zadanie.propTypes = {
    name: PropTypes.string,
    isDone: PropTypes.bool,
    points: PropTypes.number,
    author: PropTypes.string,
};

Zadanie.defaultProps = {
    name: "Zadanie",
    isDone: false,
    points: 0,
    author: "Unknown",
};

export default Zadanie;
