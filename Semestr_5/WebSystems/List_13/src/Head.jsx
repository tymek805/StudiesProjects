function Head() {
  const today = new Date();
  const date = today.toLocaleDateString();

  return (
    <div>
      <h1>Lista przedmiotów</h1>
      <p>{date}</p>
    </div>
  );
}

export default Head;
