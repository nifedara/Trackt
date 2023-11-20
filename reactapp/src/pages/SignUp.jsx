import { Box, Center, Flex, Heading, Image, Link, Text, VStack } from '@chakra-ui/react'
import React, { useEffect, useState } from 'react'
import { Link as RouterLink } from "react-router-dom"
import Luggage from "../assets/suitcase.svg"
import logo from "../assets/tracktLogo.svg"
import logoMain from "../assets/tracktLogoMain.svg"
import image from "../assets/womanTravelling.svg"
import { SignUpForm } from '../components/SignUpForm'

const bgLinks = ["/assets/tracktLogo.svg", "https://picsum.photos/1100", "https://picsum.photos/1000"]

export const SignUpPage = () => {
  const [randomNumber, setRandomNumber] = useState(0);

  const generateRandomNumber = () => {
    const randomNumber = Math.random() * 3; // Multiply by 3 to get a number between 0 and 2
    const roundedNumber = Math.floor(randomNumber); // Round down to the nearest integer
    setRandomNumber(roundedNumber); // Update the state with the generated number
  };

  useEffect(() => {
    // Set a timeout for 10 seconds (10000 milliseconds)
    const timeoutId = setTimeout(generateRandomNumber, 4000);

    // Clean up the timeout to avoid memory leaks
    return () => clearTimeout(timeoutId);
  }, []);

  return (
    <Flex h={"100vh"} w={"100vw"} color={"white"} overflow={"hidden"}>
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
        <SignUpForm />
        <Image src={Luggage} position={"absolute"} bottom={0} right={0} />
      </Box>
    </Flex>
  )
}
