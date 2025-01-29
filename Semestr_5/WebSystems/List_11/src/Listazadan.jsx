import Zadanie from './Zadanie';

function Listazadan() { 
    let zadania = [
        { name: 'Programowanie sys webowych lista 1', isDone: true },
        { name: 'Zad 2 HTML 1', isDone: true },
        { name: 'Zad 3 HTML 2', isDone: true },
        { name: 'Zad 4 CSS 1', isDone: true },
        { name: 'Zadanie 5 CSS 2', isDone: true },
        { name: 'Zad 6 javascript 1', isDone: true },
        { name: 'Zad 7 javascript 2', isDone: true },
        { name: 'Spring zad 1', isDone: true },
        { name: 'Spring zad 2', isDone: true },
        { name: 'Spring zad 3', isDone: true },
        { name: 'Zad 1 React', isDone: false },
        { name: 'Zad 2 React', isDone: false }
    ];

    return (
        <ol>
            {zadania.map((zadanie, index) => (
                <li key={index}>
                    <Zadanie name={zadanie.name} isDone={zadanie.isDone} />
                </li>
            ))}
        </ol>
    );
}

export default Listazadan;
