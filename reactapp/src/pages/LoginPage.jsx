import { Box, Center, Flex, Heading, Image, Link, Text, VStack } from '@chakra-ui/react'
import React from 'react'
import { Link as RouterLink } from "react-router-dom"
import logo from "../assets/tracktLogo.svg"
import logoMain from "../assets/tracktLogoMain.svg"
import image from "../assets/womanTravelling.svg"
import Luggage from "../assets/suitcase.svg"
import { LoginForm } from '../components/LoginForm'

export const LoginPage = () => {
  return (
    <Flex h={"100vh"} w={"100vw"} bg={"primary.800"} color={"white"} overflow={"hidden"}>
      <Box h="full" w={{ base: 0, md: "45%" }} bg={"primary.600"}>
        <Flex px={12} pt={8} flexDir={"column"} justifyContent={"space-between"} h={"full"}>
          <Link as={RouterLink} to={"/"} w={"fit-content"}>
            <Image cursor={"pointer"} src={logo} w={"3rem"} h={"3rem"} />
          </Link>
          <VStack alignItems={"start"}>
            <Heading fontSize={"3xl"}>Your travel bucket app</Heading>
            <Text fontSize={"sm"}>Put all your travel plans in one place, make itineraries and budget of each travel location. Pick your bag and travel. Monitor your progress on achieving your travel goals with Trackt.</Text>
          </VStack>
          <Box display={"inline-block"} overflow={"hidden"}>
            <Image src={image} w={"full"} h={"21rem"} />
          </Box>
        </Flex>
      </Box>
      <Box as={Center} pos={"relative"} h="full" w={{ base: "full", md: "55%" }} bg={"#eee"}>
        <Link display={{ md: "none" }} pos={"absolute"} top={3} left={3} as={RouterLink} to={"/"} w={"fit-content"}>
          <Image cursor={"pointer"} src={logoMain} w={"4rem"} h={"4rem"} />
        </Link>
        <LoginForm />
        <Image src={Luggage} position={"absolute"} bottom={0} right={0} />
      </Box>
    </Flex>
  )
}
