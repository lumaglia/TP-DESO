import './globals.css'

export default function Encabezado({ titulo } : { titulo:string}){
    return <header>
        <h2 style={{textAlign:'center'}}> {titulo} </h2>
    </header>
}