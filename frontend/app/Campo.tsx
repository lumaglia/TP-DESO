import { MapNameToApi, fieldTypes } from "../public/constants";

export default function Campo({ field, register, errors, type = fieldTypes.TEXTBOX, isRequired = false, placeholder, comboValues, validation, hidden = false }: { field: string, register: any, errors: any, type?: fieldTypes, isRequired?: boolean, placeholder?: string, comboValues?: Array<string>, validation: object, hidden?: boolean }) {
    if(!hidden){
        if(!MapNameToApi[field].startsWith('direccion.')){
            return (
                <div style={{marginRight:'5px'}}>
                    <div style={{ display: 'flex', marginLeft:'10px', marginBottom:'5px' }}>
                        {<p>{field}</p>}
                        {isRequired && <p className='error'>*</p>}

                        <p className='error' style={{marginLeft: '5px',visibility: errors[MapNameToApi[field]]?
                                'visible' : 'hidden'}}>{errors[MapNameToApi[field]] ? errors[MapNameToApi[field]].message : 'placeholder'}</p>
                    </div>

                    {
                        (type !== fieldTypes.COMBOBOX) ?
                            <input className={errors[MapNameToApi[field]] && 'error'} {...register(MapNameToApi[field], validation)} placeholder={placeholder}
                                   type={fieldTypes[type].toString() === 'TEXTBOX' ? 'text' : fieldTypes[type].toLowerCase()}/>
                            :
                            <select className={errors[MapNameToApi[field]] && 'error'} required defaultValue={placeholder !== undefined ? placeholder : ''}
                                    {...register(MapNameToApi[field], validation)}>
                                <option disabled key='' value='' hidden={true}>No Especificado</option>
                                {comboValues?.map((option) => (<option key={option} value={option}>{option}</option>))}
                            </select>
                    }
                </div>
            );
        }else{
            const value = MapNameToApi[field].split('.')[1];
            return (
                <div style={{marginRight:'5px'}}>
                    <div style={{ display: 'flex', marginLeft:'10px', marginBottom:'5px' }}>
                        {<p>{field}</p>}
                        {isRequired && <p className='error'>*</p>}

                        <p className='error' style={{marginLeft: '5px',visibility: errors.direccion?.[value] ?
                                'visible' : 'hidden'}}> {errors.direccion?.[value] ? errors.direccion?.[value].message : 'placeholder'}</p>
                    </div>

                    {
                        (type !== fieldTypes.COMBOBOX) ?
                            <input className={errors.direccion?.[value] && 'error'} {...register(MapNameToApi[field], validation)} placeholder={placeholder}
                                   type={fieldTypes[type].toString() === 'TEXTBOX' ? 'text' : fieldTypes[type].toLowerCase()}/>
                            :
                            <select className={errors[field] && 'error'} required defaultValue={placeholder !== undefined ? placeholder : ''}
                                    {...register(MapNameToApi[field], validation)}>
                                <option disabled key='' value='' hidden={true}>No Especificado</option>
                                {comboValues?.map((option) => (<option key={option} value={option}>{option}</option>))}
                            </select>
                    }
                </div>
            );
        }
    }
    else
        return(<input style={{marginRight: '5px', visibility:'hidden'}}/>)
}