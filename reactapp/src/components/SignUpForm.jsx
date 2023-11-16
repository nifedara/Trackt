import React from 'react'

export const SignUpForm = () => {
    const submit = (e) => {
        e.preventDefault()
        console.log('ddd')
    }


    return (
        <form onSubmit={submit} className='w-full'>
            <div className="flex flex-col gap-2">
                <div className='w-full flex flex-col'>
                    <label htmlFor="name" className=' text-sm font-bold tracking-wide uppercase text-left'>Name</label>
                    <input required type='text' name='name' id='name' className='input' />
                </div>
                <div className='w-full flex flex-col'>
                    <label htmlFor="email" className=' text-sm font-bold tracking-wide uppercase text-left'>Email</label>
                    <input required type='email' name='email' id='email' className='input' />
                </div>
                <div className='w-full flex flex-col'>
                    <label htmlFor="password" className=' text-sm font-bold tracking-wide uppercase text-left'>Password</label>
                    <input required type='password' name='password' id='password' className='input' />
                </div>
                <p className='text-xs text-start'>By creating an account, you agree with our terms</p>
                <button type='submit' className="btn btn-brand-700">
                    Sign Up
                </button>
            </div>
        </form>
    )
}
