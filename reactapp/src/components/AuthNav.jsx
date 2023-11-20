import { Avatar, Box, Button, ButtonGroup, Flex, HStack, Image, Link, Spacer, Text } from '@chakra-ui/react'
import { Link as RouterLink } from "react-router-dom"
import logo from "../assets/tracktLogoMain.svg"

export const Navbar = () => {
    return (
        <Box as="nav" pos={"sticky"} top={0} role="navigation" h={16} zIndex={"99999"} bg={"white"} shadow={"md"} py={4} px={{ base: 3, md: 16 }}>
            <Flex h={"full"} alignItems={"center"} gap={3}>
                <Link as={RouterLink} to={"/"} w={"fit-content"}>
                    <Image cursor={"pointer"} src={logo} w={"4rem"} h={"4rem"} />
                </Link>
                <ButtonGroup ml={5} gap={3} colorScheme='facebook' variant={"link"}>
                    <Button as={RouterLink} to={"#"}>Travels</Button>
                    <Button as={RouterLink}>Goals</Button>
                </ButtonGroup>
                <Spacer />

                <HStack>
                    <Avatar />
                    <Text color={"primary.700"} fontWeight={"bold"}>Oluwanifemi</Text>
                </HStack>
            </Flex>
        </Box>
    )
}