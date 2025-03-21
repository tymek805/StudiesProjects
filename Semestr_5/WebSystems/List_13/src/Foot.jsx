function Foot(){
    const footerStyles = {
            backgroundColor: "darkslategray",
            color: "lightgray",
            padding: "20px",
            textAlign: "center", 
            fontSize: "14px",
            marginTop: "20px", 
        };
    return (
        
        
        <footer style={footerStyles}>
            <p>Programowanie Systemów Webowych {new Date().getFullYear()}</p>
        </footer>
    )
}

export default Foot