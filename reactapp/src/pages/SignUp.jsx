import { Box, Center, Flex, Heading, Image, Text, VStack } from '@chakra-ui/react'
import React from 'react'
import logo from "../assets/tracktLogo.svg"
import image from "../assets/womanTravelling.svg"
import Luggage from "../assets/suitcase.svg"
import { SignUpForm } from '../components/SignUpForm'

export const SignUpPage = () => {
  return (
    <Flex h={"100vh"} w={"100vw"} bg={"primary.800"} color={"white"} overflow={"hidden"}>
      <Box h="full" w={{ base: 0, md: "45%" }} bg={"primary.600"}>
        <Flex px={12} pt={8} flexDir={"column"} justifyContent={"space-between"} h={"full"}>
          <Image src={logo} w={"3rem"} h={"3rem"} />
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
        <SignUpForm />
        <Image src={Luggage} position={"absolute"} bottom={0} right={0} />
      </Box>
    </Flex>
  )
}
