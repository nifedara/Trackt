import { Box, Button, ButtonGroup, Flex, Icon, IconButton, Image, Link, Spacer, VStack } from '@chakra-ui/react'
import React, { useState } from 'react'
import { BsChevronLeft, BsChevronRight, BsWhatsapp } from "react-icons/bs"
import { LuMail } from "react-icons/lu"
import { Link as RouterLink } from "react-router-dom"
import airBallon from "../assets/airBallon.png"
import boat from "../assets/boat.png"
import dock from "../assets/dock.png"
import logo from "../assets/tracktLogoMain.svg"
import AnimatedText from '../components/AnimatedText'

const slideShow = [
  { src: dock, text: "Make travel itineraries" },
  { src: boat, text: "Set and track your travel goals" },
  { src: airBallon, text: "Your travel destinations in one place" }
]

export const LandingPage = () => {
  const [counter, setCounter] = useState(0)

  const incrementCounter = () => {
    setCounter((prev) => (prev + 1) % 3)
  }

  const decrementCounter = () => {
    setCounter((prev) => {
      if (prev === 0) {
        return 2
      } else {
        return prev - 1
      }
    })
  }

  return (
    <Box h={"100vh"} w={"100vw"} bgColor={"primary.100"} bg={`url("${slideShow[counter].src}") center/cover no-repeat`} transition={"2.5s ease"}>
      {/* Nav Header */}
      <Box shadow={"md"} as={Flex} gap={3} alignItems={"center"} h={16} bg={"white"} py={4} px={{ base: 3, md: 16 }}>
        <Link as={RouterLink} to={"/"} w={"fit-content"}>
          <Image cursor={"pointer"} src={logo} w={"4rem"} h={"4rem"} />
        </Link>
        <Spacer />

        <ButtonGroup colorScheme='twitter' variant={"ghost"} size={"sm"}>
          <IconButton icon={<Icon as={BsWhatsapp} />} />
          <IconButton icon={<Icon as={LuMail} />} />
        </ButtonGroup>
        <Button as={RouterLink} to={"/login"} size={"sm"}>Login</Button>
      </Box>

      {/* Body */}
      <Flex backdropFilter={"auto"} backdropBrightness={"40%"} color={"white"} w={"full"} h={"calc(100vh - var(--chakra-sizes-16))"} flexDir={"row-reverse"}>
        <Box as={VStack} flexDir={"column"} w={{ base: "full", md: "50%" }} pr={48} h={"75%"} my={"auto"} bg={"transparent"}>
          <AnimatedText key={counter} text={slideShow[counter].text} />
          <Spacer />
          <Button as={RouterLink} colorScheme='whiteAlpha' to={"/signup"} w={"full"}>Get Started</Button>
        </Box>
      </Flex>

      <ButtonGroup display={{ base: "none", md: "flex" }} variant={"filled"} pos={"fixed"} bottom={10} left={20}>
        <IconButton onClick={decrementCounter} icon={<Icon as={BsChevronLeft} />} bgColor={"#ffffffaa"} _hover={{ bgColor: "#ffffff" }} rounded={"full"} />
        <IconButton onClick={incrementCounter} icon={<Icon as={BsChevronRight} />} bgColor={"#ffffffaa"} _hover={{ bgColor: "#ffffff" }} rounded={"full"} />
      </ButtonGroup>
    </Box>
  )
}
