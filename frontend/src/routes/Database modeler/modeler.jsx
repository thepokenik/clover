import React, {useState} from "react";
import './components/style.css';
const modeler = () =>{

    // Estado para armazenar as tabelas e suas colunas
    const [tables, setTables] = useState([]);
    // Estados para o formulário de adição de tabela
    const [newTableName, setNewTableName] = useState('');
    // Estados para o formulário de adição de coluna
    const [newColumnName, setNewColumnName] = useState('');
    const [unique, setUnique] = useState(false);
    const [autoIncrement, setAutoIncrement] = useState(false);
    const [notNull, setNotNull] = useState(false);
    const [primaryKey, setPrimaryKey] = useState(false);
    const [binary, setBinary] = useState(false);
    const [primitiveValue, setPrimitiveValue] = useState('');
    const [ADMTerminalStatus, setADMTerminalStatus] = useState({ display: 'none' });
    const [selectedTableIndex, setSelectedTableIndex] = useState(null);

     // Verifica se o nome da coluna está vazio antes de adicionar
     const verifyInput = (name, onAddColumn) => {
        if (newColumnName === "") {
            alert("Campo Nome da Coluna vazio!");
        } else {
            onAddColumn(name);
        }
    };

    // Abre ou fecha o terminal ADM com base no valor passado
    const openAndCloseADMTerminal = (value) => {
        if (value) {
            setADMTerminalStatus({ display: "block" });
        } else {
            setADMTerminalStatus({ display: "none" });
        }
    }

     // Adiciona uma nova tabela ao estado
     const addTable = () => {
        if (newTableName === "") {
            alert("Campo Nome da Tabela não pode ser vazio!");
        } else {
            const newTable = {
                name: newTableName,
                columns: [],
            };
            setTables((prevTables) => [...prevTables, newTable]);
            setNewTableName('');
        }
    }

     // Adiciona uma nova coluna à tabela selecionada
    const addColumn = () => {
        if (selectedTableIndex !== null) {
            const tableName = tables[selectedTableIndex].name;
            const newColumn = {
                name: newColumnName,
                unique,
                autoIncrement,
                notNull,
                primaryKey,
                binary,
                primitiveValue,
            };

            const updatedTables = tables.map((table, index) =>
            index === selectedTableIndex ? { ...table, columns: [...table.columns, newColumn] } : table
            );

            setTables(updatedTables);
             // Limpa os estados relacionados à adição de coluna
            setNewColumnName('');
            setUnique(false);
            setAutoIncrement(false);
            setNotNull(false);
            setPrimaryKey(false);
            setBinary(false);
            setPrimitiveValue('');
        }
    };

    // Manipulador para o envio do formulário de tabela
    const handleTableSubmit = (e) => {
        e.preventDefault();
        addTable();
    };

    // Manipulador para o envio do formulário de coluna
    const handleColumnSubmit = (e) => {
        e.preventDefault();
           // Verifica se o nome da coluna está vazio antes de adicionar
        verifyInput(tables[selectedTableIndex].name, addColumn);
    };

    return(
        <main className="modelerMain">
           <div className='rightConfi' onClick={() => openAndCloseADMTerminal(false)}>
        <h1>CloverDDL</h1>
        <div className='newTable'>
          <form onSubmit={handleTableSubmit}>
            <div className='newTableGrid'>
              <label>Nome da Tabela:</label>
              <input type="text" value={newTableName} onChange={(e) => setNewTableName(e.target.value)} />
            </div>
            <button type="submit">Adicionar Tabela</button>
          </form>
        </div>
      </div>
      <div className='screenModel' onClick={() => openAndCloseADMTerminal(false)}>
        <div className='table'>
          {tables.map((table, index) => (
            <div key={index} className='tableStyle' value={table.name} onDoubleClick={() => {setSelectedTableIndex(index); openAndCloseADMTerminal(true)}}>
              <h3>{table.name}</h3>
              <ul>
                <li>{table.name + "_ID"}</li>
              </ul>
              <ul>
                {table.columns.map((column, colIndex) => (
                  <li key={colIndex}>
                    {column.name} {column.unique ? 'UN' : ''} {column.autoIncrement ? 'AI' : ''} {column.notNull ? 'NN' : ''} {column.primaryKey ? 'PK' : ''} {column.binary ? 'B' : ''} {column.primitiveValue ? `(${column.primitiveValue})` : ''}
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>
      </div>
      <div className='ADMTerminal' style={ADMTerminalStatus}>
          <form onSubmit={handleColumnSubmit}>
            <div>
              <label>Nome da Coluna:</label>
              <div className='column'>
                <div className='nameColumn'>
                  <input type="text" value={newColumnName} onChange={(e) => setNewColumnName(e.target.value)} />
                </div>
                <div className='checkValue'>
                  <label>Unique</label>
                  <input type="checkbox" checked={unique} onChange={() => setUnique(!unique)} />
                </div>
                <div className='checkValue'>
                  <label>Auto Increment</label>
                  <input type="checkbox" checked={autoIncrement} onChange={() => setAutoIncrement(!autoIncrement)} />
                </div>
                <div className='checkValue'>
                  <label>Not Null</label>
                  <input type="checkbox" checked={notNull} onChange={() => setNotNull(!notNull)} />
                </div>
                <div className='checkValue'>
                  <label>Primary Key</label>
                  <input type="checkbox" checked={primaryKey} onChange={() => setPrimaryKey(!primaryKey)} />
                </div>
                <div className='checkValue'>
                  <label>Binary</label>
                  <input type="checkbox" checked={binary} onChange={() => setBinary(!binary)} />
                </div>
                <div className='checkValue'>
                  <select value={primitiveValue} onChange={(e) => setPrimitiveValue(e.target.value)} required>
                    <option value="">Primitive Type</option>
                    <option value="varchar">Varchar</option>
                    <option value="char">Char</option>
                    <option value="int">Int</option>
                  </select>
                </div>
              </div>
            </div>
            <input type="submit" value="Adicionar Coluna" />
          </form>
        </div>
        </main>
    )
}
export default modeler;