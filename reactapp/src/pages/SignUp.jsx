import React from 'react'
import tracktLogo from "../assets/tracktLogo.svg"
import womanTravelling from "../assets/womanTravelling.svg"
import suitcase from "../assets/suitcase.svg"
import { SignUpForm } from '../components/SignUpForm'

export const SignUpPage = () => {
  return (
    <div className="h-screen w-screen overflow-hidden">
      <div className="flex h-full w-full">
        {/* Left Side */}
        <div className="hidden w-[45%] bg-brand-600 px-24 pt-6 md:flex flex-col justify-between text-white">
          <img src={tracktLogo} className='h-12 w-12' alt='Trackt' />
          <div className="">
            <h1 className='text-3xl font-semibold'>Your travel bucket app</h1>
            <p className='text-justify'>Put all your travel plans in one place, make itineraries and budget of each travel location. Pick your bag and travel. Monitor your progress on achieving your travel goals with Trackt.</p>
          </div>
          <img src={womanTravelling} alt="woman with box" className='w-full h-[20rem]' />
        </div>

        {/* Right Side */}
        <div className="w-full relative bg-[#efefef] md:w-[55%] flex justify-center items-center">
          <div class="block w-8/12 rounded-3xl bg-white shadow text-center p-6">
            <h5 class="mb-2 text-xl font-bold tracking-wide text-brand-700">
              Create your account
            </h5>
            <SignUpForm />

          </div>
          <img src={suitcase} className='absolute bottom-0 -right-10 hidden md:block' alt="" srcset="" />
        </div>
      </div>
    </div>
  )
}
